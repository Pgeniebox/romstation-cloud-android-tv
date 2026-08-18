# Implementation Plan - Robust Signaling and UI Stability

This plan addresses the signaling timeout (failed handshake) and the persistent UI crashes. It focuses on ensuring data integrity during signaling and making the script's DOM manipulation more resilient.

## User Review Required

> [!IMPORTANT]
> I am switching to a more robust string escaping method (`JSONObject.quote`) for all data sent from Java to JavaScript. This is the most likely cause of the "Failed to connect" error, as malformed SDP strings would prevent the handshake from completing.

## Proposed Changes

### Java Implementation

#### [MODIFY] [MainActivity.java](file:///E:/Users/Admin/AndroidStudioProjects/cloudxSolution/app/src/main/java/com/world/cloudxsolution/MainActivity.java)
-   **Safe JS Injection**: Use `JSONObject.quote()` for SDP offers, answers, and ICE candidates. This ensures that newlines and special characters in the SDP do not break the JavaScript string literals.
-   **Logging**: Added logging to confirm when JS is being evaluated.

#### [MODIFY] [WebRtcReceiver.java](file:///E:/Users/Admin/AndroidStudioProjects/cloudxSolution/app/src/main/java/com/world/cloudxsolution/WebRtcReceiver.java)
-   **Fix Abstract Class Error**: Fix the instantiation of `SdpObserverAdapter` by using an anonymous class implementation.
-   **SDP Type Handling**: Ensure `handleRemoteAnswer` correctly sets the remote description as an `ANSWER`.

### JavaScript Logic

#### [MODIFY] [script.js](file:///E:/Users/Admin/AndroidStudioProjects/cloudxSolution/app/src/main/assets/script.js)
-   **Enhanced Logging**: Add logs in `handleOffer`, `handleAnswer`, and `handleIceCandidate` to confirm they are receiving data from Java.
-   **DOM Resilience**: Refactor `appendChild` calls in `createButton` and `createElement` to be null-safe.
-   **Header Guard**: Add a null check in `HeaderSection.checkHeader` to prevent the `appendChild` crash if the Xbox header hasn't loaded yet.

## Verification Plan

### Automated Tests
-   Build the project using Gradle.

### Manual Verification
-   Monitor Logcat for `[BxC] handleOffer from Java`. If this appears, the escaping fix worked.
-   Check for `Remote answer set successfully` in Logcat.
-   Verify the `iceConnectionState` transitions to `connected`.
-   Confirm the `appendChild` crash is resolved and the "Better xCloud" settings button appears (if applicable).
