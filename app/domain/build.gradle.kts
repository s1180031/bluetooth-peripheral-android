val kotlin_version: String by project
val koin_version: String by project

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.2")

    defaultConfig {
        minSdkVersion(29)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    androidExtensions {
        isExperimental = true
        features = setOf("parcelize")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.appcompat:appcompat:1.2.0")

    //region koin
    // Koin for Android
    implementation("org.koin:koin-android:$koin_version")
    // Koin Android Scope features
    implementation("org.koin:koin-androidx-scope:$koin_version")
    // Koin AndroidX Experimental features
    implementation("org.koin:koin-androidx-ext:$koin_version")
    //endregion

    //region BLE
    implementation("no.nordicsemi.android:ble:2.2.4")
    implementation("no.nordicsemi.android:ble-common:2.2.4")

    //endregion
    //endregion
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
}
