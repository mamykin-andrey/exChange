package ru.mamykin.exchange.ui.activity

import android.os.Bundle
import ru.mamykin.exchange.core.platform.BaseActivity

/**
 * Activity for displaying the splash screen
 */
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(RootActivity.newInstance(this))
    }
}