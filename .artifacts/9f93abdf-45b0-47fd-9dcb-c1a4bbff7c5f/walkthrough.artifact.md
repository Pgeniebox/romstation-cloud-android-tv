# Walkthrough - Separate Player Setup with Delayed Start

I have refactored the player initialization logic to use specialized methods for each player type and ensured that both players wait for 10 seconds before starting the stream.

## Changes Made

### `GameActivity.java`

- **Separated Player Methods**:
    - `setupExoPlayer(String rtspUri)`: Contains all logic for configuring and starting ExoPlayer.
    - `setupAlexVasPlayer(String rtspUri)`: Contains logic for initializing and starting the AlexVas player.
- **Unified 10s Delay**: In `joinLobby`, the `retryHandler.postDelayed` now wraps the player selection. Regardless of the player chosen via `USE_ALEXVAS_PLAYER`, the app waits 10 seconds before calling the respective setup method.
- **Removed AlexVas Retry**: The `RtspStatusListener` and all associated retry logic for the AlexVas player have been removed as requested. It now performs a simple `init` and `start`.
- **Cleanup**:
    - Removed `streamRetryHandler` and related constants.
    - Removed `onRtspStatus...` callback methods.
    - Updated `onDestroy` to correctly stop `rtspSurfaceView` if it's active.

## Verification

### Log Observation
You can confirm the delay and player start by watching for these logs:
1. `GameActivity: Stream URI: rtsp://...` (Logged immediately)
2. *10-second pause*
3. `GameActivity: ALEXVAS START` (if enabled) OR `GameActivity: EXOPLAYER START` (if disabled)

### How to Switch Players
In `GameActivity.java`, toggle the following flag:
- `private static final boolean USE_ALEXVAS_PLAYER = true;` (Default)
- `private static final boolean USE_ALEXVAS_PLAYER = false;` (To use ExoPlayer)
