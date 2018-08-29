package ru.mamykin.exchange.presentation.presenter

import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.mamykin.exchange.presentation.router.SplashRouter

class SplashPresenterTest {

    @Mock
    lateinit var router: SplashRouter

    lateinit var presenter: SplashPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SplashPresenter(router)
    }

    @Test
    fun onFirstViewAttach_shouldOpenRootScreen() {
        presenter.onFirstViewAttach()

        verify(router).openRootScreen()
    }
}