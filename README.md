# CloudX - Unified Cloud Gaming for Android TV

**Status: Active Development**

CloudX is a high-performance, open-source Android application designed to provide a native, low-latency cloud gaming experience for **Xbox Cloud Gaming**, **RomStation**, and **PlayStation Now**. 

This project is the first to move core streaming logic away from browser-based WebViews and into a **Pure Native Java** environment, optimized specifically for Android TV devices like the Mi Box.

## 👨‍💻 Developed by
**LaGab Adel** - [GitHub Profile](https://github.com/Pgeniebox)

---

## 🚀 Key Innovations: The Native Handover

Traditional cloud gaming clients often run entirely inside a browser engine (WebView). This adds significant overhead, limits controller compatibility, and introduces input lag. 

**CloudX changes the architectural approach:**
1. **Lightweight Selection**: We use WebViews only for the login and game selection process (for Xbox, RomStation, and PlayStation).
2. **Native Execution**: Once a session starts, the app intercepts the signaling and "hands over" the connection to a native background service.
3. **Hardware Acceleration**: Video streams are rendered using a native `SurfaceView` with direct hardware decoding, bypassing the browser's rendering stack for maximum performance.

---

## ✨ Features

### 🎮 Unified Entry Point
- **Launcher Selection**: A dedicated selection screen allows you to choose between Romstation, Xbox, and PlayStation modes at startup.
- **Android TV Optimized**: Leanback UI support and 1080p/1440p resolution forcing.

### 💚 Xbox Cloud Gaming
- **Ultra-Low Latency**: Native WebRTC implementation for immediate response times.
- **Microphone Support**: Full native support for in-game voice chat.
- **Performance Diagnostics**: Real-time overlay showing bitrate, FPS, and jitter.
- **Advanced Gamepad Support**: 
    - Reliable (TCP-like) and Unreliable (UDP-like) input channels.
    - Low-level rumble support.
    - Custom deadzone and sensitivity settings.
    - Specialized Xbox Guide (Nexus) button mapping.

### 🕹️ RomStation Cloud
- **Native RTSP Streaming**: High-performance RTSP implementation via VLC/ExoPlayer.
- **Hardware Decoding**: Optimized for TV hardware (like Mi Box) to offload processing to native Java.
- **Custom Gamepad Mapping**: Full support for mapping physical controllers to RomStation cloud inputs.

### 💙 PlayStation Now
- **Native WebBridge**: Optimized WebView environment with hardware-accelerated rendering.
- **Custom User Agent**: Spoofs a native PlayStation Now desktop environment for full service access.
- **Automated Injection**: Dynamic JS injection (`PS.js`) to enhance UI and improve compatibility with TV navigation.

---

## 🛠 Getting Started

### Prerequisites
- Android 10 (API 29) or higher.
- A valid subscription for the respective service (Xbox Game Pass Ultimate, RomStation, or PlayStation Now).

### Building from Source
1. Clone the repository:
   ```bash
   git clone https://github.com/Pgeniebox/xbox-cloud-Solution-Android-tv.git
   ```
2. Open the project in **Android Studio Koala** or newer.
3. Build and run:
   ```bash
   ./gradlew assembleDebug
   ```

## 📜 Acknowledgements

Special thanks to the following teams for their research and open-source contributions to the cloud gaming ecosystem:

- **Better xCloud** ([@redphx](https://github.com/redphx/better-xcloud)) - For extensive research and scripts.
- **XStreaming** ([@Geocld](https://github.com/Geocld/XStreaming)) - For their innovative approach to xCloud streaming.

## ⚖️ License

Distributed under the **MIT License**. See `LICENSE` for more information.

---
*Note: This is an unofficial project and is currently in active development. Tested on Mi Box TV.*

*Disclaimer: This project is not affiliated with, endorsed by, or sponsored by Microsoft, Xbox, RomStation, or PlayStation.*
