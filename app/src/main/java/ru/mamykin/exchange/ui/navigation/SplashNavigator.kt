package ru.mamykin.exchange.ui.navigation

import android.app.Activity
import ru.mamykin.exchange.presentation.router.SplashRouter
import ru.mamykin.exchange.ui.activity.RootActivity

class SplashNavigator(
        private val activity: Activity
) : SplashRouter {

    override fun openRootScreen() {
        activity.startActivity(RootActivity.newInstance(activity))
        activity.finish()
    }
}