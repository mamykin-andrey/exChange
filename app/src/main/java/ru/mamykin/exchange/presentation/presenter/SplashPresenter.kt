package ru.mamykin.exchange.presentation.presenter

import ru.mamykin.exchange.presentation.router.SplashRouter
import javax.inject.Inject

class SplashPresenter @Inject constructor(
        private val router: SplashRouter
) {
    fun onFirstViewAttach() {
        router.openRootScreen()
    }
}