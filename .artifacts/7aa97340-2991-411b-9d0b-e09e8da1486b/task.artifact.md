# Task List - Raw Bluetooth Gamepad Data Processor

- `[x]` Create `HexUtils.java` for readable logging output.
- `[x]` Create `BluetoothRawProcessor.java` with connection and reading threads.
- `[x]` Create `layout_bluetooth_test.xml` for the testing UI.
- `[x]` Create `BluetoothTestActivity.java` to handle UI and processor integration.
- `[x]` Register `BluetoothTestActivity` in `AndroidManifest.xml`.
- `[x]` Add a navigation trigger in `MainActivity.java`.
- `[x]` Add "Scan" button to `layout_bluetooth_test.xml`.
- `[x]` Implement Bluetooth discovery in `BluetoothTestActivity.java`.
- `[x]` Add `BroadcastReceiver` to handle discovered devices.
- `[x]` Update permissions for `BLUETOOTH_SCAN` and `ACCESS_FINE_LOCATION`.
- `[ ]` Implement HID UUID support in `BluetoothRawProcessor.java`.
- `[ ]` Implement reflection-based socket fallback in `BluetoothRawProcessor.java`.
- `[ ]` Add connection retry logic for multiple strategies.
- `[ ]` Verify by checking the UI log during gamepad use.
