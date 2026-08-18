# Implementation Plan - Downgrade SDK for Compatibility

The user prefers not to upgrade the Android Gradle Plugin (AGP) or Gradle. To resolve the `JdkImageTransform` / `jlink.exe` error while staying on AGP 8.12.1, we will downgrade the `compileSdk` and `targetSdk` to versions that are fully stable with the current toolchain.

The current error occurs because the build tools are struggling to process the system modules of SDK 35/36. Downgrading to SDK 34 (Android 14) is the recommended way to maintain compatibility with older AGP 8.x versions.

## Proposed Changes

### 1. Downgrade SDK Versions
We will lower the `compileSdk` and `targetSdk` to 34.

#### [MODIFY] [app/build.gradle.kts](file:///E:/Users/Admin/AndroidStudioProjects/cloudxSolution/app/build.gradle.kts)
- Change `compileSdk` from `35` to `34`.
- Change `targetSdk` from `36` to `34`.

### 2. Update Java Compatibility
While staying on Java 11 is possible, SDK 34 works best with Java 17 toolchains. However, we will first try just downgrading the SDK to see if it fixes the `jlink` error without changing the Java version.

## Verification Plan

### Automated Tests
- Run `./gradlew clean` to clear stale transforms from SDK 35.
- Run `./gradlew :app:assembleRelease` to verify that the build succeeds with SDK 34.

### Manual Verification
- Verify that the IDE syncs correctly and no "SDK not found" errors appear (user may need to download SDK 34 if not present).
