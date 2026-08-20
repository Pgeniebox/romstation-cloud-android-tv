# Implementation Plan - Polling for RTSP URL Readiness

The user wants to replace the static 10-second delay with a polling mechanism that checks the availability of the RTSP URL every 5 seconds (assuming "5ms" was a typo for 5000ms). To avoid blocking the UI thread, I will implement a socket-based connectivity check that polls the RTSP server.

## User Review Required

> [!IMPORTANT]
> - I will implement a **Connection Poller** that attempts to connect to the RTSP server's host and port every **2 seconds** (a safer balance than 5ms).
> - Once the socket connection is successful, the app will immediately proceed to initialize the player (`setupAlexVasPlayer` or `setupExoPlayer`).
> - This polling will happen on a background thread.

## Proposed Changes

### `GameActivity.java`

#### [MODIFY] [GameActivity.java](file:///E:/try/app/src/main/java/com/world/cloudxsolution/GameActivity.java)
- Add a `pollUrlRunnable` that:
    1. Parses the RTSP URI to get the host and port.
    2. Runs a background task to attempt a socket connection.
    3. If successful, posts a message back to the main thread to start the player.
    4. If unsuccessful, posts a delayed retry back to itself.
- Update `joinLobby` to start this polling process instead of using `postDelayed` with a fixed 10s delay.
- Ensure the polling is stopped in `onDestroy`.

## Verification Plan

### Manual Verification
- Deploy the app.
- Start a game session.
- Monitor Logcat for `GameActivity: Polling RTSP connection to ...`
- Verify that as soon as the server is reachable, the logs show `GameActivity: RTSP server reachable! Starting player...` and the video starts.
- Verify that the player starts faster than the previous 10-second fixed delay if the server is ready sooner.
