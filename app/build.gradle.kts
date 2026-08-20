
plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.world.cloudxsolution"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.world.cloudxsolution"
        minSdk = 29
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    splits {
        abi {
            isEnable = true
            reset()
            include("arm64-v8a", "armeabi-v7a")
            isUniversalApk = false
        }
    }

    buildTypes {

        release {

            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        aidl = true
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.webkit)
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
    implementation(libs.gson)
    implementation(libs.okhttp)
    implementation(libs.libvlc)
    implementation(libs.androidx.core.splashscreen)
    implementation("com.github.alexeyvasilyev:rtsp-client-android:5.7.1")
}