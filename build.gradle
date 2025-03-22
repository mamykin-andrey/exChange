//buildscript {
//    ext.kotlin_version = '2.1.0'
//    repositories {
//        google()
//        jcenter()
//    }
//    dependencies {
//        classpath 'com.android.tools.build:gradle:8.7.3'
//        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
//    }
//}
//
//allprojects {
//    repositories {
//        google()
//        jcenter()
//    }
//}
//
//task clean(type: Delete) {
//    delete rootProject.buildDir
//}
plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
}