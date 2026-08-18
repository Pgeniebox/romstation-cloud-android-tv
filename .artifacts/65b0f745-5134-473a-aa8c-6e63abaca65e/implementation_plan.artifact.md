# Implementation Plan - Inject Playout Delay in script.js

Implement the `forceZeroPlayoutDelay` function in `script.js` to support extreme low-latency by injecting the `playout-delay` RTP header extension into the SDP. Since `script.js` does not use the `sdp-transform` library, a regex-based implementation will be used to maintain consistency with existing SDP manipulation functions.

## User Review Required

> [!NOTE]
> This change will add a new function `forceZeroPlayoutDelay` to `script.js` and call it during the `createOffer` phase of the WebRTC handshake. It will specifically target the `video` media section of the SDP.

## Proposed Changes

### JavaScript (WebView Script)

#### [MODIFY] [script.js](file:///E:/Users/Admin/AndroidStudioProjects/cloudxSolution/app/src/main/assets/script.js)
- Implement `forceZeroPlayoutDelay(sdp)` after `setCodecPreferences(sdp, preferredCodec)`.
- Use regex to find the `m=video` section and inject the `a=extmap` line with an unused extension ID.
- Update `createOffer` in the `RTCPeerConnection` proxy to call `forceZeroPlayoutDelay(offer.sdp)` if the extreme low-latency setting is enabled (or just call it if that's the intention).
    - Note: I'll check if `stream.extreme_low_latency` exists in prefs; if not, I'll just add the function and call it as requested.

## Verification Plan

### Automated Tests
- Run a build to ensure no regression in assets: `gradlew :app:assembleDebug`

### Manual Verification
- Verify in Logcat that "Extreme Low-Latency: Injected playout-delay" appears during connection.
- Verify that the stream starts successfully with the modified SDP.
