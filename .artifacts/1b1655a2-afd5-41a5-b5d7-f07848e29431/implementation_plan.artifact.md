# Move Gamepad Deadzone and Sensitivity to Script Settings

This plan describes how to move `STICK_DEADZONE` and `CAMERA_SENSITIVITY` constants from `AndroidGamepadListener.java` to the "script settings" (JavaScript side) and allow them to be modified via the app's settings UI.

## User Review Required

> [!NOTE]
> The default values will be preserved: Deadzone = 0.12 (12%) and Sensitivity = 1.5x (150%).
> These settings will be available under the "Controller" tab in the "Better xCloud" settings menu.

## Proposed Changes

### Android Component

#### [MODIFY] [AndroidGamepadListener.java](file:///E:/Users/Admin/AndroidStudioProjects/cloudxSolution/app/src/main/java/com/world/cloudxsolution/AndroidGamepadListener.java)
- Remove `private static final float STICK_DEADZONE = 0.12f;`
- Remove `private static final float CAMERA_SENSITIVITY = 1.5f;`
- Add `private float stickDeadzone = 0.12f;`
- Add `private float cameraSensitivity = 1.5f;`
- Add `public void setStickDeadzone(float deadzone)` and `public void setCameraSensitivity(float sensitivity)` methods.
- Update `deadzone(float value)` to use `stickDeadzone`.
- Update `applySensitivity(float value)` to use `cameraSensitivity`.

#### [MODIFY] [WebRtcReceiver.java](file:///E:/Users/Admin/AndroidStudioProjects/cloudxSolution/app/src/main/java/com/world/cloudxsolution/WebRtcReceiver.java)
- Add `public void updateGamepadSettings(float deadzone, float sensitivity)` method that updates the `gamepadListener` if it's not null.

#### [MODIFY] [WebRtcBridge.java](file:///E:/Users/Admin/AndroidStudioProjects/cloudxSolution/app/src/main/java/com/world/cloudxsolution/WebRtcBridge.java)
- Add `@JavascriptInterface` method `setGamepadSettings(float deadzone, float sensitivity)` to forward settings to `WebRtcReceiver`.

---

### JavaScript Component

#### [MODIFY] [script.js](file:///E:/Users/Admin/AndroidStudioProjects/cloudxSolution/app/src/main/assets/script.js)
- Add `controller.deadzone` and `controller.sensitivity` to `StreamSettingsStorage.DEFINITIONS`.
- Add `controller.deadzone` and `controller.sensitivity` to `ALL_PREFS.stream`.
- Update `SettingsManager.SETTINGS` to include handlers for these new settings that call `window.AndroidBridge.setGamepadSettings`.
- In the `main()` function, add an event listener for `state.playing` to push the current settings to Java when a stream starts.
- Add these items to `TAB_CONTROLLER_ITEMS` in `SettingsDialog` to expose them in the UI.

## Verification Plan

### Automated Tests
- Build the project to ensure no syntax errors.

### Manual Verification
1.  Open the app and navigate to "Better xCloud" settings.
2.  Go to the "Controller" tab.
3.  Modify the "Deadzone" and "Camera Sensitivity" settings.
4.  Observe (via Logcat or feel) that the settings are applied to the `AndroidGamepadListener`.
5.  Start a game and verify that the settings are applied correctly at startup.
