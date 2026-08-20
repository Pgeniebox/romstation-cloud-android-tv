# Implementation Plan - Refactored Gamepad Mapping

Refactor the gamepad mapping system to use direct map lookups (HashMap) for performance and ensure D-pad direction handling in `onGenericMotionEvent`.

## Proposed Changes

### 1. Gamepad Mapper Refactoring
#### [MODIFY] [GamepadMapper.java](file:///E:/try/app/src/main/java/com/world/cloudxsolution/GamepadMapper.java)
- Replace `Map<Integer, Binding>` with:
    - `Map<Integer, Integer> buttonToIdMap`: Maps physical Android KeyCodes to protocol IDs (1-25).
    - `Map<String, Integer> axisToIdMap`: Maps Axis strings (e.g., "AXIS_X:-1") to protocol IDs (1-25).
- Update default mappings to include D-pad axes (`AXIS_HAT_X`, `AXIS_HAT_Y`) which are common on most gamepads.
- Provide helper methods for direct lookup: `getButtonId(int keyCode)` and `getAxisId(int axis, int direction)`.

### 2. Input Processing Optimization
#### [MODIFY] [GameActivity.java](file:///E:/try/app/src/main/java/com/world/cloudxsolution/GameActivity.java)
- **Buttons**: In `onKeyDown`/`onKeyUp`, perform a direct `get()` from `buttonToIdMap`. No more loops.
- **Axes**: In `onGenericMotionEvent`, iterate only over the axes present in the current event and perform direct `get()` lookups from `axisToIdMap`.
- **D-pad Support**: Ensure `onGenericMotionEvent` explicitly checks `AXIS_HAT_X` and `AXIS_HAT_Y` and maps them using the new direct lookup.

### 3. Mapping UI Update
#### [MODIFY] [GameActivity.java](file:///ed E:/try/app/src/main/java/com/world/cloudxsolution/GameActivity.java)
- Update `startCapture` and `showGamepadMappingDialog` to populate the new `buttonToIdMap` and `axisToIdMap` structures.
- Ensure the UI correctly displays the current mapping by reversing the lookup (ID -> physical input).

## Verification Plan

### Automated Tests
- Build using `:app:assembleDebug`.

### Manual Verification
1. Launch the app and open the "Gamepad Mapping" dialog.
2. Verify that the D-pad (HAT axes) can be mapped if they are not picked up by KeyEvents.
3. Map a button and verify it triggers the correct action in-game.
4. Verify that there is no noticeable input lag due to processing loops.
