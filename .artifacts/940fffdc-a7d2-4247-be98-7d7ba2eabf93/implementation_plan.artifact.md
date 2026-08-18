# Implementation Plan - Forcing Low-Latency Video Codec (H.264)

The goal is to force the WebRTC connection to use H.264 instead of VP8/VP9. H.264 is preferred for cloud gaming because it has near-universal hardware decoding support on Android, resulting in much lower latency and better battery efficiency.

## User Review Required

> [!IMPORTANT]
> We will modify the codec selection logic to place H.264 at the absolute beginning of the preference list. This tells the server that H.264 is our "must-use" codec.

## Proposed Changes

### [Component Name] WebRTC Logic

#### [MODIFY] [WebRtcReceiver.java](file:///E:/Users/Admin/AndroidStudioProjects/cloudxSolution/app/src/main/java/com/world/cloudxsolution/WebRtcReceiver.java)
- Refactor `setVideoCodecPreferences` to:
    1.  Separate the available codecs into two groups: "Preferred" (H.264) and "Others".
    2.  Create a new list with all H.264 variants at the very top.
    3.  Follow H.264 with their associated support codecs (like `rtx` for H.264).
    4.  Append all other codecs to the end of the list as fallbacks.
- Update the sorting logic to be more aggressive, ensuring no Google codecs (VP8/VP9/AV1) appear before H.264.

## Verification Plan

### Automated Tests
- Build the project to ensure `RtpCapabilities` and `CodecCapability` types are handled correctly.

### Manual Verification
- Start a streaming session.
- Check the logcat for the message `Applied video codec preferences`.
- Verify that **H.264** now appears at the very beginning of the list.
- Observe the stream quality and latency; it should feel "snappier" on most hardware.
