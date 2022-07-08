object BuildPlugins {
    const val kotlin = "kotlin"
    const val detekt = "io.gitlab.arturbosch.detekt"
    const val javaLibrary = "java-library"

    const val androidApplication = "com.android.application"
    const val kotlinKapt = "kotlin-kapt"
    const val androidLibrary = "com.android.library"
    const val androidLint = "com.android.lint"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val androidHilt = "dagger.hilt.android.plugin"

    const val googleServices = "com.google.gms.google-services"
    const val firebaseCrashlytics = "com.google.firebase.crashlytics"
    const val safeArgs = "androidx.navigation.safeargs.kotlin"
    const val firebasePerformance = "com.google.firebase.firebase-perf"
}

/**
 * Gradle dependencies classpath notations used by the Common SDK Repository.
 */
object ClasspathDependencies {
    const val detektFormatting = "io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.DETEKT}"
}

/**
 * Gradle Publish Plugins used by the Common SDK Repository.
 */
object PublishPlugins {
    const val maven = "maven-publish"
    const val artifactory = "com.jfrog.artifactory"
}

