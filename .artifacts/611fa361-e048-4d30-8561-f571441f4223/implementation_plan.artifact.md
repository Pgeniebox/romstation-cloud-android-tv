# Implementation Plan - Radial Deadzone for Both Sticks

This plan replaces the per-axis deadzone logic with a more natural radial deadzone for both the left and right analog sticks in `AndroidGamepadListener`.

## Proposed Changes

### Android App (:app)

#### [MODIFY] [AndroidGamepadListener.java](file:///E:/Users/Admin/AndroidStudioProjects/cloudxSolution/app/src/main/java/com/world/cloudxsolution/AndroidGamepadListener.java)
- Update `onGenericMotion` to:
    - Get raw X/Y values for the left stick.
    - Calculate the left stick magnitude.
    - Apply radial deadzone to the left stick.
    - Get raw X/Y values for the right stick using `activeLayout`.
    - Calculate the right stick magnitude.
    - Apply radial deadzone to the right stick.
    - Apply `cameraSensitivity` to the right stick values.
- Remove the old `deadzone(float value, int lastV)` method and the `lastValue` field as they will no longer be needed.

## Verification Plan

### Manual Verification
1. Start a game streaming session.
2. Test the **Left Stick**:
    - Verify movement feels smooth in all 360 degrees.
    - Ensure there is no "sticking" on the X or Y axes.
3. Test the **Right Stick**:
    - Verify camera movement is smooth and responds to the `cameraSensitivity` setting.
    - Ensure the deadzone correctly filters out minor stick jitter.
