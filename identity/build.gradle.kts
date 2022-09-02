plugins {
    id (BuildPlugins.androidLibrary)
    id (BuildPlugins.kotlinAndroid)
    kotlin (BuildPlugins.androidExtensions)
    id (BuildPlugins.kotlinKapt)
    id (BuildPlugins.androidHilt)
}

android {
    compileSdk = Versions.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = Versions.MIN_SDK_VERSION
        targetSdk = Versions.TARGET_SDK_VERSION

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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

    implementation(libs.android.material)
    implementation(libs.android.activity)
    implementation(libs.android.appCompat)
    implementation(libs.android.constraintLayout)
    implementation(libs.android.coreKtx)
    implementation(libs.android.fragment)
    implementation(libs.android.annotation)
    implementation(libs.android.navigation.fragment)
    implementation(libs.android.navigation.ui)
    implementation(libs.android.lifecycle.livedata)
    implementation(libs.android.lifecycle.viewmodel)
    implementation(libs.android.lifecycle.runtime)

    implementation(libs.android.hilt)
    kapt(libs.android.hilt.compiler)

    implementation(project(":core-framework"))
    implementation(project(":core-ui"))

    // firebase
    implementation("com.google.firebase:firebase-auth-ktx:21.0.6")

    testImplementation(libs.test.junit)
}