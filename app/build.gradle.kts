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

    signingConfigs {
        create("release") {
            storeFile = file("/Users/mj/Library/Mobile Documents/com~apple~CloudDocs/KEYSTORE/FUNDITO/FUNDITO")
            storePassword = "ㅋ"
            keyAlias = "FUNDITO"
            keyPassword = "ㅋ"
        }
    }
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
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


    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).all {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

}


dependencies {
    //region DEFAULT
    implementation(fileTree(mapOf("dir" to "libs", "include" to arrayOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}")

    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.1.0")

    implementation("com.google.android.material:material:1.2.0-alpha03")
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
    testImplementation("androidx.room:room-testing:2.2.3")


    //region 3rd party Libraries

    /**
     * Retrofit
     */
    implementation("com.squareup.retrofit2:retrofit:2.6.2")
    implementation("com.squareup.retrofit2:converter-gson:2.6.2")
    implementation("com.squareup.okhttp3:logging-interceptor:4.2.2")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.6.2")

    /**
     * Glide
     */
    implementation("com.github.bumptech.glide:glide:4.10.0")
    kapt("com.github.bumptech.glide:compiler:4.10.0")

    /**
     * Timber for logging
     */
    implementation("com.jakewharton.timber:timber:4.7.1")


    /**
     * Dagger for DI
     */
    implementation("com.google.dagger:dagger:2.24")
    implementation("com.google.dagger:dagger-android-support:2.24")
    kapt("com.google.dagger:dagger-compiler:2.24")
    kapt("com.google.dagger:dagger-android-processor:2.24")

    /**
     * Dexter for permissions
     */
    implementation("com.karumi:dexter:6.0.1")

    /**
     * Coroutine
     */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.2")

    /**
     * Flow Binding
     */
    implementation("io.github.reactivecircus.flowbinding:flowbinding-android:0.6.0")
    implementation("io.github.reactivecircus.flowbinding:flowbinding-material:0.6.0")

    /**
     * AutofitTextView
     */
    implementation("me.grantland:autofittextview:0.2.1")

    /**
     * Firebase
     */
    implementation ("com.google.firebase:firebase-auth:19.2.0")
    implementation ("com.google.firebase:firebase-messaging:20.1.0")

    /**
     * Facebook
     */
    implementation ("com.facebook.android:facebook-android-sdk:5.13.0")
    implementation("com.facebook.android:facebook-share:5.13.0")


    /**
     * Circle ImageView
     */
    implementation ("de.hdodenhof:circleimageview:3.0.1")


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

apply {
    plugin("com.google.gms.google-services")
}
