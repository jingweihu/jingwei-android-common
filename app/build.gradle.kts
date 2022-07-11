plugins {
    id (BuildPlugins.androidApplication)
    id (BuildPlugins.kotlinAndroid)
    kotlin (BuildPlugins.androidExtensions)
    id (BuildPlugins.kotlinKapt)
    id (BuildPlugins.androidHilt)
    id (BuildPlugins.googleServices)
}

android {
    signingConfigs {
        create("release") {
            storeFile =
                file("/Users/jingweih/Documents/android/jingwei-android-common/android-key-store")
            storePassword = "123456"
            keyAlias = "key"
            keyPassword = "123456"
        }
    }
    compileSdk = Versions.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = "link.jingweih.android.sandboxapp"
        minSdk = Versions.MIN_SDK_VERSION
        targetSdk = Versions.TARGET_SDK_VERSION
        versionCode = Versions.VERSION_CODE
        versionName = Versions.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        getByName("debug") {
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // Flipper
    if (rootProject.hasProperty("mavenTestEnable")) {
        val commonVersion = "1.0.3"
        debugImplementation("link.jingweih.android:flipper:${commonVersion}")
        releaseImplementation("link.jingweih.android:flipper-no-op:${commonVersion}")
        implementation("link.jingweih.android:identity:${commonVersion}")
    } else {
        debugImplementation(project(":flipper"))
        releaseImplementation(project(":flipper-no-op"))
        implementation(project(":identity"))
    }
    implementation("com.google.firebase:firebase-common-ktx:20.1.1")

    //ANDROID
    implementation(libs.google.material)
    implementation(libs.android.activity)
    implementation(libs.android.appCompat)
    implementation(libs.android.constraintLayout)
    implementation(libs.android.coreKtx)
    implementation(libs.android.fragment)
    implementation(libs.android.navigation.fragment)
    implementation(libs.android.navigation.ui)
    implementation(libs.android.lifecycle.livedata)
    implementation(libs.android.lifecycle.viewmodel)
    implementation(libs.android.hilt)
    kapt(libs.android.hilt.compiler)

    testImplementation(libs.test.junit)
}