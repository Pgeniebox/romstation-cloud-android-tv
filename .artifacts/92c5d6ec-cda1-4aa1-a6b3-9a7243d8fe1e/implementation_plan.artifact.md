# Implementation Plan - Launcher Selection Screen

The goal is to create a new entry point for the application that allows the user to choose between "Romstation" and "Xbox" modes. This will involve creating a new activity, its layout, and updating the manifest to correctly handle the new flow and include all necessary components.

## Proposed Changes

### New Activity

#### [NEW] [SelectionActivity.java](file:///E:/try/app/src/main/java/com/world/cloudxsolution/SelectionActivity.java)
- A new `AppCompatActivity` that displays two options.
- Clicking "Romstation" launches `com.world.cloudxsolution.romstation.MainActivity`.
- Clicking "Xbox" launches `com.world.cloudxsolution.xbox.MainActivity`.

#### [NEW] [activity_selection.xml](file:///E:/try/app/src/main/res/layout/activity_selection.xml)
- A simple layout with two buttons or cards for selection.

### Manifest Updates

#### [MODIFY] [AndroidManifest.xml](file:///E:/try/app/src/main/AndroidManifest.xml)
- Add `SelectionActivity` and make it the `LAUNCHER` activity.
- Remove `LAUNCHER` intent filters from `com.world.cloudxsolution.romstation.MainActivity`.
- Add `com.world.cloudxsolution.xbox.MainActivity`.
- Add `com.world.cloudxsolution.xbox.StreamingService` with the required process and foreground service type.

## Verification Plan

### Automated Tests
- Run `./gradlew assembleDebug` to ensure everything compiles.

### Manual Verification
- Deploy the app and verify the selection screen appears.
- Test that both "Romstation" and "Xbox" buttons correctly launch their respective activities.
