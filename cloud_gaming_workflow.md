# RomStation Cloud Gaming Android Implementation Workflow

This document details the complete lifecycle and technical requirements for porting the RomStation cloud gaming client to Android.

## 1. Security & Authentication
All native API calls require an `auth` parameter which is an AES-encrypted JSON string.

### AES Encryption Details
- **Algorithm**: `AES/CBC/PKCS5Padding`
- **Key (16 bytes)**: `Kkvkhj43d3fPr5hg` (Bytes: `75, 107, 118, 107, 104, 106, 52, 51, 100, 51, 102, 80, 114, 53, 104, 103`)
- **Structure**: The encrypted output must be `IV (16 bytes) + EncryptedData`, then Base64 encoded.

### Initial Native Login
After the user logs into the site via a WebView, you must perform a native handshake to get the `session_key`.

- **Endpoint**: `POST https://www.romstation.fr/romstation/scripts/account/login.php`
- **Payload**: `v=229&os=1&auth=[ENCRYPTED_JSON]`
- **JSON Structure for Auth**:
```json
{
  "soft_id": 229,
  "soft_raw_uid": "ANDROID_ID",
  "soft_uid": "MD5(ANDROID_ID)",
  "phpsessid": "FROM_WEBVIEW_COOKIE",
  "member_id": 0,
  "member_session": null
}
```
- **Response**: Extract `member.session_key` and `member.id`. These are your `member_session` and `member_id` for all future calls.

---

## 2. Game Discovery
Find the specific `file_id` that is enabled for cloud play.

- **Endpoint**: `GET https://www.romstation.fr/romstation/scripts/game/get_infos.php?v=229&os=1&gid=[GAME_ID]`
- **Logic**: Parse `game.files`. Pick the entry where `status == 1`, `cloud == 1`, and `cloud_state == 4`.
- **Example**: `gameId: 84809` -> `fileId: 72022`.

---

## 3. Session Initialization (Lobby)
Initialize the remote server instance.

### Step 1: Create Lobby
- **Endpoint**: `POST https://www.romstation.fr/romstation/scripts/multiplayer/create_lobby.php`
- **Parameters**:
    - `auth`: Encrypted JSON (now including `member_id` and `member_session`).
    - `game_file_id`: `72022`
    - `cloud`: `1`
    - `live`: `1`
    - `slots`: `4`
    - `region`: `0`
- **Response**: `lobby.id`.

### Step 2: Join Lobby
- **Endpoint**: `POST https://www.romstation.fr/romstation/scripts/multiplayer/join_lobby.php`
- **Parameters**: `auth`, `lobby_id`.
- **Critical Data Extracted**:
    - `stream.uri`: The RTSP address for the video.
    - `controller.server`: The UDP IP/Port for inputs.
    - `credential.controller_id`: Byte for UDP header.
    - `credential.controller_key`: Byte for UDP header.
    - `vpn`: Boolean. If true, connect to RomStation VPN first.

---

## 4. Gameplay Streaming

### Video/Audio (RTSP)
- Use **ExoPlayer** with the RTSP module.
- Set `latency` to `100ms`.
- Ensure H.264 hardware decoding is enabled.

### Control IPC (UDP)
Send 13-byte UDP packets to the `controller.server` address.

**Packet Structure**:
- `Byte 0`: `0x01`
- `Byte 1`: `controller_id`
- `Byte 2`: `controller_key`
- `Byte 3-4`: `sequence_number` (Short, Big-Endian, increments per packet)
- `Byte 5`: Bitmask (Up, Down, Left, Right, A, B, X, Y)
- `Byte 6`: Bitmask (L1, R1, L2_Digital, R2_Digital, Select, Start, Home)
- `Byte 7`: Left Stick X (Signed Byte: -128 to 127)
- `Byte 8`: Left Stick Y (Signed Byte: -128 to 127)
- `Byte 9`: L2 Analog (Byte: 0 to 127)
- `Byte 10`: Right Stick X (Signed Byte: -128 to 127)
- `Byte 11`: Right Stick Y (Signed Byte: -128 to 127)
- `Byte 12`: R2 Analog (Byte: 0 to 127)

---

## 5. Session Commands (HTTP)
Operations like saving/loading state are **not** UDP. They are HTTP POSTs.

- **Endpoint**: `POST https://www.romstation.fr/romstation/scripts/multiplayer/lobby_cmd.php`
- **Parameters**:
    - `auth`: Encrypted token.
    - `lobby_id`: Your current lobby.
    - `action`: `send_session_command`
    - `value`: `save_state`, `load_state`, or `reset`.


## 6. Android TV User Interface and Navigation

The application is designed specifically for Android TV devices.

The UI must be fully usable without a touchscreen.

### Supported Input Devices

* TV remote
* Bluetooth mouse
* Air mouse
* Game controller
* Keyboard

---

### WebView Mouse Implementation

A virtual mouse cursor must be implemented on top of the `WebView`.

The cursor should be rendered as an overlay and translated into `MotionEvent` events.

Supported operations:

* Single click
* Double click
* Long click
* Drag and drop
* Hover
* Vertical scrolling
* Horizontal scrolling

---

### Cursor Controls

| Input             | Action       |
| ----------------- | ------------ |
| D-pad             | Move cursor  |
| Left analog stick | Move cursor  |
| DPAD_CENTER       | Left click   |
| BUTTON_A          | Left click   |
| BUTTON_B          | Back         |
| MENU              | Context menu |
| ESC               | Browser back |

---

### Scrolling

**Vertical scrolling**

Supported through:

* Mouse wheel
* D-pad Up/Down
* Right analog stick

**Horizontal scrolling**

Supported through:

* Shift + mouse wheel
* D-pad Left/Right
* Left analog stick

---

### WebView Requirements

Enable:

```java
settings.setJavaScriptEnabled(true);
settings.setDomStorageEnabled(true);
settings.setSupportMultipleWindows(true);
settings.setMediaPlaybackRequiresUserGesture(false);
```

The `WebView` must correctly dispatch:

* Touch events
* Mouse events
* Hover events
* Focus events
* Wheel events
* Keyboard events

---

## 7. TV-Specific Features

The application should also provide:

* Full-screen mode
* Overscan-safe UI
* Controller-first navigation
* Focus indicators
* Auto-hide mouse cursor
* Session resume after inactivity
* TV-friendly dialogs and menus
* 10-foot UI layout optimized for remote viewing
