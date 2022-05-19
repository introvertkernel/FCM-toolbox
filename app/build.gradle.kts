import de.fayard.refreshVersions.core.versionFor

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

val versionMajor = 1
val versionMinor = 8
val versionPatch = 3
val versionBuild = 0

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.gb.fcm"
        minSdk = 19
        targetSdk = 30
        versionCode = versionMajor * 1000000 + versionMinor * 10000 + versionPatch * 100 + versionBuild
        versionName = "$versionMajor.$versionMinor.$versionPatch"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true

        javaCompileOptions {
            annotationProcessorOptions {
                argument("room.schemaLocation", "$projectDir/schemas")
                argument("room.incremental", "true")
                argument("room.expandProjection", "true")
            }
        }
    }
    buildFeatures {
        viewBinding = true
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    /* AndroidX */
    implementation(AndroidX.appCompat)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.core.ktx)
    implementation(AndroidX.lifecycle.liveDataKtx)
    implementation(AndroidX.lifecycle.viewModelKtx)
    implementation(AndroidX.preferenceKtx)
    implementation(AndroidX.recyclerView)
    implementation(AndroidX.transition)
    androidTestImplementation(AndroidX.test.espresso.core)
    androidTestImplementation(AndroidX.test.ext.junit)

    /* Material Design */
    implementation(Google.Android.material)

    /* Firebase */
    implementation(platform(Firebase.bom))
    implementation(Firebase.cloudMessaging)
    implementation(Firebase.realtimeDatabase)

    /* Koin: Dependency Injection */
    implementation(Koin.android)
    testImplementation(Koin.test)
    androidTestImplementation(Koin.test)

    /* Moshi: JSON parsing */
    implementation(Square.moshi)
    implementation(Square.moshi.kotlinReflect)
    kapt(Square.moshi.kotlinCodegen)
    versionFor(Square.moshi)
    implementation("com.squareup.moshi:moshi-adapters:${versionFor(Square.moshi)}")

    /* Room: SQLite persistence */
//    implementation(AndroidX.room.runtime)
//    kapt(AndroidX.room.compiler)
//    kapt("androidx.room:room-compiler:2.4.2")
//    implementation(AndroidX.room.ktx)
//    testImplementation(AndroidX.room.testing)

    val roomVersion = "2.4.2"

    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$roomVersion")
    // To use Kotlin Symbolic Processing (KSP)
//    ksp("androidx.room:room-compiler:$roomVersion")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")

    // optional - RxJava2 support for Room
    implementation("androidx.room:room-rxjava2:$roomVersion")

    // optional - RxJava3 support for Room
    implementation("androidx.room:room-rxjava3:$roomVersion")

    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation("androidx.room:room-guava:$roomVersion")

    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$roomVersion")

    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:2.5.0-alpha01")

    /* Kotlin Coroutines */
    implementation(KotlinX.coroutines.core)
    implementation(KotlinX.coroutines.android)
    implementation(KotlinX.coroutines.playServices)

    /* JUnit */
    testImplementation(Testing.junit4)

}

apply(plugin = "com.google.gms.google-services")
