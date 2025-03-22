package ru.mamykin.exchange

import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()