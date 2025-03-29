import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { localProperties.load(it) }
}
val apiKey = localProperties.getProperty("exchangeapikey") ?: ""

kotlin {
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
            baseName = "Shared"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.android)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.mockk.common)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(libs.junit)
                implementation(libs.mockk)
            }
        }
        val commonMain by getting {
            kotlin.srcDir("$buildDir/generated/commonMain/kotlin")
        }
    }
}

android {
    namespace = "ru.mamykin.exchange.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

tasks.register("generateApiKeyFile") {
    doLast {
        val dir = project.file("$buildDir/generated/commonMain/kotlin/ru/mamykin/exchange/config")
        dir.mkdirs()

        val file = File(dir, "ApiKey.kt")
        file.writeText(
            """
            // Generated file, do not edit!
            object ApiKey {
                const val VALUE = "$apiKey"
            }
        """.trimIndent()
        )
    }
}

tasks.named("preBuild") {
    dependsOn("generateApiKeyFile")
}