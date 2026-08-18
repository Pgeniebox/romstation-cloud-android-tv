# Setup Release Signing

To build a release APK/AAB, you need a digital signature. I will help you configure the project to use a signing key securely.

## User Review Required

> [!WARNING]
> **Never commit your `.jks` (keystore) file or `keystore.properties` file to Version Control (Git).** I will update your `.gitignore` to prevent this.

> [!IMPORTANT]
> You will need to manually generate the keystore file using the Android Studio UI as shown in the steps below.

## Proposed Changes

### Configuration
#### [MODIFY] [.gitignore](file:///E:/Users/Admin/AndroidStudioProjects/cloudxSolution/.gitignore)
- Add `*.jks`, `*.keystore`, and `keystore.properties` to ensure sensitive files stay local.

#### [NEW] [keystore.properties.example](file:///E:/Users/Admin/AndroidStudioProjects/cloudxSolution/keystore.properties.example)
- Create a template file to show you where to put your key details.

#### [MODIFY] [build.gradle.kts](file:///E:/Users/Admin/AndroidStudioProjects/cloudxSolution/app/build.gradle.kts)
- Add logic to load signing configurations from `keystore.properties`.
- Configure the `release` build type to use these credentials.

## Step-by-Step Instructions

### 1. Generate the Key
If the Android Studio UI fails, you can use the terminal.

#### Option A: Android Studio UI
1. Go to **Build > Generate Signed Bundle / APK...**
2. Select **APK** and click **Next**.
3. Under **Key store path**, click **Create new...**.
4. Choose a location and fill in the details. **Ensure you fill in all certificate fields.**

#### Option B: Terminal (Command Line)
Run this in the terminal:
```powershell
& "E:\Program Files\Android\Android Studio\jbr\bin\keytool.exe" -genkey -v -keystore release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias release-alias
```

### 2. Fill in the Details
I will create a `keystore.properties` file for you. You will just need to update it with the values you used.

## Verification Plan

### Manual Verification
- After applying the changes and filling in your `keystore.properties`, run:
  `./gradlew :app:assembleRelease`
- Verify that a signed APK is generated in `app/build/outputs/apk/release/`.
