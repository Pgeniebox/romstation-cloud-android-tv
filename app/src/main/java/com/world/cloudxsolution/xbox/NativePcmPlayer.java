package com.world.cloudxsolution.xbox;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.util.Log;

public class NativePcmPlayer {
    private static final String TAG = "NativePcmPlayer";
    private AudioTrack audioTrack;
    private int currentSampleRate = 48000;
    private long frameCount = 0;

    public NativePcmPlayer() {
        init();
    }

    public synchronized void setSampleRate(int rate) {
        if (rate <= 0 || rate == currentSampleRate) return;
        Log.i(TAG, "Changing sample rate from " + currentSampleRate + " to " + rate);
        currentSampleRate = rate;
        release();
        init();
    }

    private void init() {
        try {
            int minBufferSize = AudioTrack.getMinBufferSize(
                    currentSampleRate,
                    AudioFormat.CHANNEL_OUT_STEREO,
                    AudioFormat.ENCODING_PCM_16BIT);

            // Use a larger buffer (4x min or 64KB) for stable playback on TV
            int bufferSize = Math.max(minBufferSize * 4, 65536);

            audioTrack = new AudioTrack.Builder()
                    .setAudioAttributes(new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build())
                    .setAudioFormat(new AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(currentSampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_STEREO)
                            .build())
                    .setBufferSizeInBytes(bufferSize)
                    .setTransferMode(AudioTrack.MODE_STREAM)
                    .build();

            if (audioTrack.getState() == AudioTrack.STATE_INITIALIZED) {
                audioTrack.play();
                Log.i(TAG, "Native PCM Player initialized: " + currentSampleRate + "Hz, Stereo, Buffer: " + bufferSize);
            } else {
                Log.e(TAG, "AudioTrack initialization failed state: " + audioTrack.getState());
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to init AudioTrack", e);
        }
    }

    public synchronized void play(byte[] data) {
        if (audioTrack == null || audioTrack.getState() != AudioTrack.STATE_INITIALIZED) {
            return;
        }
        
        frameCount++;
        if (frameCount % 100 == 0) {
            //Log.d(TAG, "Data flow active: received " + data.length + " bytes (chunk #" + frameCount + ")");
        }

        try {
            if (audioTrack.getPlayState() != AudioTrack.PLAYSTATE_PLAYING) {
                audioTrack.play();
            }
            int result = audioTrack.write(data, 0, data.length);
            if (result < 0) {
                Log.e(TAG, "Write error: " + result);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error writing to AudioTrack", e);
        }
    }

    public void release() {
        synchronized (this) {
            if (audioTrack != null) {
                try {
                    if (audioTrack.getState() == AudioTrack.STATE_INITIALIZED) {
                        audioTrack.stop();
                        audioTrack.flush();
                    }
                    audioTrack.release();
                    audioTrack = null;
                    Log.i(TAG, "Native PCM Player released");
                } catch (Exception ignored) {}
            }
        }
    }
}
