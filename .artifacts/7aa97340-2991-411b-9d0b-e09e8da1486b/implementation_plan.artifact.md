# Implementation Plan - Bluetooth Socket Fallback Strategy

The goal is to fix the `IOException: read failed` error by implementing a more robust connection strategy. This includes trying multiple UUIDs (SPP and HID) and using a reflection-based fallback if standard methods fail.

## User Review Required

> [!WARNING]
> - **Exclusive Connection**: If the gamepad is a standard HID device, Android might prevent a direct socket connection while it's already using the device for system input. You may need to "Unpair" or disconnect the device from system settings before the app can "claim" it via raw socket.
> - **Reflection Usage**: We will use a hidden Android API (`createRfcommSocket`) as a fallback. This is a common workaround for the "Socket might closed or timeout" error.

## Proposed Changes

### Bluetooth Communication Component Improvements

#### [MODIFY] [BluetoothRawProcessor.java](file:///E:/Users/Admin/AndroidStudioProjects/cloudxSolution/app/src/main/java/com/world/cloudxsolution/BluetoothRawProcessor.java)
- Add `UUID_HID` (`00001124-0000-1000-8000-00805f9b34fb`).
- Implement a connection retry loop that tries:
    1. Standard HID UUID.
    2. Standard SPP UUID.
    3. Reflection-based fallback on Port 1 (bypassing SDP).
- Improve error reporting to indicate which method failed.

## Verification Plan

### Manual Verification
- Attempt to connect to the gamepad in the app.
- Monitor Logcat to see if the fallback methods are triggered.
- Verify if the HID UUID or the reflection method successfully establishes a connection.


