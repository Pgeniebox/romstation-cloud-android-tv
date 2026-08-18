# Walkthrough - Release Signing Setup

I have configured your project to support signed release builds. Since you encountered an error while creating the key through the UI, I've provided a terminal command as an alternative.

## Changes Made

### Security
- **Updated [.gitignore](file:///E:/Users/Admin/AndroidStudioProjects/cloudxSolution/.gitignore)**: Added rules to ensure your keystore (`.jks`) and property files containing passwords are NEVER uploaded to Git.

### Infrastructure
- **Created [keystore.properties.example](file:///E:/Users/Admin/AndroidStudioProjects/cloudxSolution/keystore.properties.example)**: A template file showing you where to put your signing details.
- **Updated [build.gradle.kts](file:///E:/Users/Admin/AndroidStudioProjects/cloudxSolution/app/build.gradle.kts)**: Added logic to automatically read credentials from a file named `keystore.properties` (if it exists) and use them to sign your release APK.

## How to Finish the Setup

### 1. Generate the Key via Terminal
If the UI is failing, run this command in your Android Studio terminal (Alt+F12):

```powershell
& "E:\Program Files\Android\Android Studio\jbr\bin\keytool.exe" -genkey -v -keystore release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias release-alias
```
- It will ask for a password (use something you can remember).
- It will ask for your name and organization details.
- It will create `release-key.jks` in your project folder.

### 2. Create your Properties File
1. Copy `keystore.properties.example` and rename the copy to `keystore.properties`.
2. Open `keystore.properties` and fill in the passwords and alias you used in Step 1:
   ```properties
   storeFile=release-key.jks
   storePassword=your_password
   keyAlias=release-alias
   keyPassword=your_password
   ```

### 3. Build the Release APK
Once the files are ready, run:
`./gradlew :app:assembleRelease`

Your signed APK will be located at:
`app/build/outputs/apk/release/app-release.apk`
