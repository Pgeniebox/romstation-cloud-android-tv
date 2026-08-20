# Implementation Plan - Automatic Seek to Live Edge with Safety Buffer

The user wants the player to automatically seek as close as possible to the live edge (max seekable position minus 30ms) whenever the player enters the `STATE_READY` state.

## Proposed Changes

### GameActivity.java

#### [MODIFY] [GameActivity.java](file:///E:/try/app/src/main/java/com/world/cloudxsolution/GameActivity.java)

- Modify `onPlaybackStateChanged` within the `Player.Listener`:
    - When `state == Player.STATE_READY`:
        - Retrieve the current duration using `player.getDuration()`.
        - Calculate the target position: `long targetPosition = player.getDuration() - 30;`.
        - Ensure the target is non-negative and duration is valid (`C.TIME_UNSET`).
        - Log the window duration, current position, and the calculated seek target.
        - Call `player.seekTo(targetPosition)`.

## Verification Plan

### Manual Verification
- Deploy the app and start a stream.
- Monitor Logcat for "Seek to Edge" logs.
- Verify that the player stays at the live edge (low latency) and recovers quickly after buffering events.
