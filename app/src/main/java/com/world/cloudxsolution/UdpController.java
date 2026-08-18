package com.world.cloudxsolution;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class UdpController {

    private static final String TAG = "UdpController";

    private DatagramSocket socket;
    private volatile InetAddress address;
    private final String hostname;
    private int port;
    private byte controllerId;
    private byte controllerKey;
    private short sequenceNumber = 0;
    private long packetCount = 0;
    private long sendCallCount = 0;
    private long droppedBeforeResolveCount = 0;
    private volatile boolean closed = false;
    private final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "UdpControllerThread-" + threadNumber.getAndIncrement());
            t.setPriority(Thread.MAX_PRIORITY);
            return t;
        }
    });

    public UdpController(String ip, int port, byte controllerId, byte controllerKey) {
        android.util.Log.d(TAG, "Initializing with IP=" + ip + ", Port=" + port + ", ID=" + controllerId + ", Key=" + controllerKey);
        this.hostname = ip;
        this.port = port;
        this.controllerId = controllerId;
        this.controllerKey = controllerKey;
        try {
            this.socket = new DatagramSocket();
            // Set low latency traffic class (IPTOS_LOWDELAY = 0x10)
            this.socket.setTrafficClass(0x10);
            // Increase send buffer size to 64KB
            this.socket.setSendBufferSize(536);
            android.util.Log.d(TAG, "DatagramSocket created, local port=" + this.socket.getLocalPort() + ", trafficClass=" + this.socket.getTrafficClass());
        } catch (Exception e) {
            android.util.Log.e(TAG, "FAILED to create DatagramSocket", e);
            return;
        }

        // IMPORTANT: InetAddress.getByName() performs a blocking DNS lookup.
        // This constructor is called directly from GameActivity's network
        // response callback, which runs on the MAIN thread - resolving here
        // used to throw NetworkOnMainThreadException, which was silently
        // swallowed, leaving `address` permanently null and making every
        // later socket.send() fail with "null buffer || null address".
        // Resolve on the executor's background thread instead.
        executor.execute(() -> {
            try {
                InetAddress resolved = InetAddress.getByName(hostname);
                address = resolved;
                android.util.Log.d(TAG, "Resolved " + hostname + " -> " + resolved.getHostAddress());
            } catch (Exception e) {
                android.util.Log.e(TAG, "FAILED to resolve host " + hostname, e);
            }
        });
    }

    public void sendInput(byte buttons1, byte buttons2, byte lx, byte ly, byte l2, byte rx, byte ry, byte r2) {
        sendCallCount++;
        if (sendCallCount <= 5 || sendCallCount % 100 == 0) {
            android.util.Log.d(TAG, "sendInput() called, count=" + sendCallCount
                    + ", socket=" + socket + ", closed=" + closed + ", address=" + address);
        }
        try {
            boolean hasData = buttons1 != 0 || buttons2 != 0 || lx != 0 || ly != 0
                    || l2 != 0 || rx != 0 || ry != 0 || r2 != 0;

            ByteBuffer buffer = ByteBuffer.allocate(13);
            buffer.order(ByteOrder.BIG_ENDIAN);
            buffer.put((byte) 0x01);
            buffer.put(controllerId);
            buffer.put(controllerKey);
            buffer.putShort(++sequenceNumber);
            if (hasData) {
                buffer.put(buttons1);
                buffer.put(buttons2);
                buffer.put(lx);
                buffer.put(ly);
                buffer.put(l2);
                buffer.put(rx);
                buffer.put(ry);
                buffer.put(r2);
            }

            byte[] data = buffer.array();
            int length = buffer.position();
            executor.execute(() -> {
                try {
                    if (address == null) {
                        // DNS resolution hasn't finished yet (or failed) - drop this
                        // frame rather than crash; the next heartbeat tick will retry.
                        droppedBeforeResolveCount++;
                        if (droppedBeforeResolveCount <= 5) {
                            android.util.Log.w(TAG, "Dropping packet, address not resolved yet (drop #" + droppedBeforeResolveCount + ")");
                        }
                        return;
                    }
                    if (socket != null && !socket.isClosed()) {
                        DatagramPacket packet = new DatagramPacket(data, length, address, port);
                        socket.send(packet);
                        packetCount++;
                        if (packetCount <= 5 || packetCount % 100 == 0) {
                            android.util.Log.d(TAG, "Sent " + packetCount + " packets to "
                                    + address.getHostAddress() + ":" + port + " (last len=" + length + ")");
                        }
                    } else {
                        android.util.Log.w(TAG, "Skipped send: socket is null or closed");
                    }
                } catch (Exception e) {
                    android.util.Log.e(TAG, "socket.send() FAILED", e);
                }
            });
        } catch (RejectedExecutionException e) {
            android.util.Log.e(TAG, "executor.execute() REJECTED - executor was already shut down", e);
        } catch (Exception e) {
            android.util.Log.e(TAG, "sendInput() FAILED before dispatch", e);
        }
    }

    public void close() {
        android.util.Log.d(TAG, "close() called, total sendInput() calls=" + sendCallCount
                + ", total packets actually sent=" + packetCount);
        closed = true;
        executor.shutdown();
        if (socket != null) {
            socket.close();
            socket = null;
        }
    }
}