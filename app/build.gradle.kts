plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Constants.compileSdk)
    defaultConfig {
        applicationId = Constants.appId
        minSdkVersion(Constants.minSdk)
        targetSdkVersion(Constants.compileSdk)
        versionCode = Constants.versionCode
        versionName = Constants.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables.useSupportLibrary = true

        javaCompileOptions {
            annotationProcessorOptions {
                arguments = mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    dataBinding.isEnabled = true


    sourceSets {
        getByName("androidTest") {
            assets.srcDirs(File("$projectDir/schemas"))
        }
    }
}

dependencies {
    //region DEFAULT
    implementation(fileTree(mapOf("dir" to "libs", "include" to arrayOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}")

    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.1.0")

    implementation("com.google.android.material:material:1.2.0-alpha02")
    //endregion

    /**
     * For [MotionLayout] version upper 2.0.0
     */
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-beta4")

    //region Android Architecture Components (AAC)

    //Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0-rc03")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:1.0.0-rc03")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0-rc03")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0-rc03")
    kapt("androidx.lifecycle:lifecycle-compiler:2.2.0-rc03")

    //Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.2.0-rc04")
    implementation("androidx.navigation:navigation-ui-ktx:2.2.0-rc04")

    //Room
    implementation("androidx.room:room-runtime:2.2.3")
    kapt("androidx.room:room-compiler:2.2.3")
    implementation("androidx.room:room-ktx:2.2.3")
    implementation("androidx.room:room-rxjava2:2.2.3")
    testImplementation("androidx.room:room-testing:2.2.3")


    //WorkManager
    implementation("androidx.work:work-runtime-ktx:2.2.0")
    implementation("androidx.work:work-rxjava2:2.2.0")
    androidTestImplementation("androidx.work:work-testing:2.2.0")
    //endregion

    //region 3rd party Libraries

    /**
     * Retrofit
     */
    implementation("com.squareup.retrofit2:retrofit:2.6.2")
    implementation("com.squareup.retrofit2:converter-gson:2.6.2")
    implementation("com.squareup.retrofit2:adapter-rxjava:2.6.2")
    implementation("com.squareup.okhttp3:logging-interceptor:4.2.2")

    /**
     * Glide
     */
    implementation("com.github.bumptech.glide:glide:4.10.0")
    kapt("com.github.bumptech.glide:compiler:4.10.0")




    //endregion


}

dependencies {
    testImplementation("androidx.test:core:1.2.0")
    testImplementation("junit:junit:4.12")

    testImplementation("androidx.test:runner:1.2.0")
    testImplementation("androidx.test:rules:1.2.0")

    testImplementation("androidx.test.ext:junit:1.1.1")
    testImplementation("androidx.test.ext:truth:1.2.0")
    testImplementation("com.google.truth:truth:1.0")

    testImplementation("androidx.test.espresso:espresso-core:3.2.0")
    testImplementation("androidx.test.espresso:espresso-contrib:3.2.0")

    testImplementation("org.mockito:mockito-core:3.1.0")
    testImplementation("android.arch.core:core-testing:1.1.1")

    testImplementation("org.robolectric:robolectric:4.3.1")
}

dependencies {
    androidTestImplementation("androidx.test:core:1.2.0")

    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test:rules:1.2.0")

    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.ext:truth:1.2.0")
    androidTestImplementation("com.google.truth:truth:1.0")

    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.2.0")

    androidTestImplementation("android.arch.core:core-testing:1.1.1")
}
