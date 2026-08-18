# Fix GamepadIndex extraction in script.js

The user is unable to extract `gamepadIndex` from the JSON data sent to the `control` channel in `script.js`.
Analysis of the current code shows that the extraction logic is inside an unreachable block when `data` is a string. Additionally, the parsing logic is overly complex and contains errors (e.g., checking `jd.gamepadIndex` where `jd` is a string, and not handling `0` as a valid index).

## User Review Required

> [!IMPORTANT]
> This change simplifies the `send` method in the proxy `RTCPeerConnection` in `script.js` to correctly handle both binary and string data, and specifically extracts `gamepadIndex` when JSON data is sent over the `control` channel.

## Proposed Changes

### script.js

#### [MODIFY] [script.js](file:///E:/Users/Admin/AndroidStudioProjects/cloudxSolution/app/src/main/assets/script.js)

- Refactor the `send` method within `createDataChannel`.
- Correctly identify when `data` is a string.
- Parse `data` as JSON when `label` is `'control'`.
- Extract `gamepadIndex` from the parsed object, ensuring `0` is handled correctly.
- Call `window.AndroidBridge.gamepadIndex` with the extracted value.

```javascript
// Before
send: function(data) {
    if(label=='input'||label=='unreliableinput'||label=='reliableinput')return;
    if (window.AndroidBridge) {
        let isBinary = typeof data !== 'string';
        if (isBinary) {
            window.AndroidBridge.onDataChannelSend(label, new Uint8Array(data), null, true);
            return;
        }

        let base64;
        if (isBinary) {
            base64 = window.bxToBase64(data);
        } else {
            if (typeof data !== "string"){
                try{
                    let jd= new TextDecoder().decode(Uint8Array.from(atob(data), c => c.charCodeAt(0)) );
                    js=JSON.parse(jd);
                    if(jd.gamepadIndex){
                        window.AndroidBridge.gamepadIndex(jd.gamepadIndex);}
                    console.log("sendin strin to channel: " + label+" : " +  new TextDecoder().decode(
                        Uint8Array.from(atob(data), c => c.charCodeAt(0)) ));
                }catch(e){
                    console.log('json err: '+e);
                }
            }else{
                console.log("sendin data to channel: " + label+" : " +  data)
            }
            // Encode text string to base64 using UTF-8
            base64 = window.btoa(unescape(encodeURIComponent(data)));
        }
        window.AndroidBridge.onDataChannelSend(label, null, base64, isBinary);
    }
},

// After
send: function(data) {
    if (label === 'input' || label === 'unreliableinput' || label === 'reliableinput') return;
    if (window.AndroidBridge) {
        let isBinary = typeof data !== 'string';
        if (isBinary) {
            window.AndroidBridge.onDataChannelSend(label, new Uint8Array(data), null, true);
            return;
        }

        // data is a string
        console.log("sendin data to channel: " + label + " : " + data);

        if (label === 'control') {
            try {
                let js = JSON.parse(data);
                if (js && typeof js.gamepadIndex !== 'undefined') {
                    window.AndroidBridge.gamepadIndex(js.gamepadIndex);
                }
            } catch (e) {
                console.log('[BxC] control json parse err: ' + e);
            }
        }

        // Encode text string to base64 using UTF-8
        let base64 = window.btoa(unescape(encodeURIComponent(data)));
        window.AndroidBridge.onDataChannelSend(label, null, base64, false);
    }
},
```

## Verification Plan

### Manual Verification
- Deploy the app and start streaming.
- Connect/disconnect a gamepad or change gamepad state.
- Verify in Logcat that `WebRtcBridge: gamepadIndex: X` is logged.
- Verify that a Toast message `gamepadIndex: X` appears on the device.
