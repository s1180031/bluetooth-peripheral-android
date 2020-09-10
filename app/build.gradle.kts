import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlin_version: String by project
val nav_version: String by project
val fragment_version: String by project
val koin_version: String by project

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.2")

    defaultConfig {
        applicationId = "com.github.s1180031.bluetoothperipheral"
        minSdkVersion(29)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))
    implementation(project(":app:domain"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.1")
    implementation("com.google.firebase:firebase-analytics:17.5.0")
    implementation("com.google.firebase:firebase-crashlytics:17.2.1")
    implementation("com.google.firebase:firebase-perf:19.0.8")

    //region fragment
    implementation("androidx.fragment:fragment-ktx:$fragment_version")
    debugImplementation("androidx.fragment:fragment-testing:$fragment_version")
    //endregion

    //region NavigationArchitectureComponents
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")
    //endregion

    //region koin
    // Koin for Android
    implementation("org.koin:koin-android:$koin_version")
    // Koin AndroidX Scope features
    implementation("org.koin:koin-androidx-scope:$koin_version")
    // Koin AndroidX ViewModel features
    implementation("org.koin:koin-androidx-viewmodel:$koin_version")
    // Koin AndroidX Fragment features
    implementation("org.koin:koin-androidx-fragment:$koin_version")
    // Koin AndroidX Experimental features
    implementation("org.koin:koin-androidx-ext:$koin_version")
    //endregion

    testImplementation("junit:junit:4.12")

    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}
