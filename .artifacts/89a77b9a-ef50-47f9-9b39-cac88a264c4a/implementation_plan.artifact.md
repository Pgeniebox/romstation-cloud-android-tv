# Implementation Plan - Fix Gradle Sync Error (NoSuchMethodError)

The project is currently failing to sync with the following error:
`java.lang.NoSuchMethodError: 'org.gradle.api.artifacts.Dependency org.gradle.api.artifacts.dsl.DependencyHandler.module(java.lang.Object)'`
at `com.android.build.gradle.internal.res.Aapt2FromMaven$Companion.create(Aapt2FromMaven.kt:136)`.

This error indicates a version mismatch between the **Android Gradle Plugin (AGP)** and the **Gradle** version. The `module(Object)` method was removed in Gradle 9.0, but some versions of AGP (or specific configurations) still attempt to use it, leading to this failure.

Currently, the project uses **AGP 9.3.0** and **Gradle 9.5.0**.

## User Review Required

> [!IMPORTANT]
> I will align the AGP and Gradle versions to a known compatible pair: **AGP 9.1.0** and **Gradle 9.3.1**. This combination is officially documented as compatible and should resolve the missing method error.

## Proposed Changes

### Build Configuration

#### [MODIFY] [libs.versions.toml](file:///E:/Users/Admin/AndroidStudioProjects/cloudxSolution/gradle/libs.versions.toml)
- Downgrade `agp` version from `9.3.0` to `9.1.0`.

#### [MODIFY] [gradle-wrapper.properties](file:///E:/Users/Admin/AndroidStudioProjects/cloudxSolution/gradle/wrapper/gradle-wrapper.properties)
- Downgrade Gradle `distributionUrl` from `9.5.0` to `9.3.1`.

## Verification Plan

### Automated Tests
- **Gradle Sync**: Run the `gradle_sync` tool to verify that the project configuration completes successfully.
- **Build Environment**: Run `./gradlew buildEnvironment` to ensure dependencies are resolved without the `NoSuchMethodError`.
- **Assemble Debug**: Run `./gradlew assembleDebug` (if sync passes) to verify that AAPT2 is correctly fetched and resources are compiled.

### Manual Verification
- Verify that the IDE no longer shows the sync error banner.
