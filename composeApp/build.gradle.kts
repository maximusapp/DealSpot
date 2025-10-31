import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
//    id("org.jetbrains.kotlin.native.cocoapods")
    alias(libs.plugins.kotlinCocoapods)
}

kotlin {
    cocoapods {
        version = "1.0.0"
        name = "ComposeApp"
        summary = "ComposeApp"
        homepage = "https://example.com"
        ios.deploymentTarget = "14.0"
        framework {
            baseName = "ComposeApp"
            isStatic = true
        }
        // Generate cinterop for Google Maps so platform.GoogleMaps is available in Kotlin
//        pod("GoogleMaps")
        pod("GoogleMaps") {
            version = libs.versions.pods.google.maps.get()
            extraOpts += listOf("-compiler-option", "-fmodules")
        }

        pod("Google-Maps-iOS-Utils") {
            moduleName = "GoogleMapsUtils"
            version = libs.versions.pods.google.ios.maps.utils.get()
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            linkerOpts("-Wl,-ios_version_min,14.0")
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.accompanist.permissions)
            implementation(projects.network)
            //DI
            implementation(libs.koin.android)
            implementation(libs.coil3.network.okhttp)
            implementation(libs.play.services.maps)
        }
        commonMain.dependencies {
            implementation(projects.network)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.androidx.datastore.preferences)
            implementation(libs.kotlinx.coroutines.core)
            //DI
            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            implementation(libs.ktor.serialization)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
            implementation(libs.compose.navigation)
            implementation(libs.coil3.core)
            implementation(libs.coil3.compose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.app.dealspot"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.app.dealspot"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

