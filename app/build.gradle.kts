plugins {
    id("com.android.application")
    // ❌ usuwamy Kotlina i Compose
}

android {
    namespace = "com.example.smartsenior"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.smartsenior"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Ustawienia kompilacji dla Javy
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // Wyłączamy Compose i włączamy klasyczny layout XML
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // AndroidX i Material Design
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")

    // Testy (opcjonalne)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}
