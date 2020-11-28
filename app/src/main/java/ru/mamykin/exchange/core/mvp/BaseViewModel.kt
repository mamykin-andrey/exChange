package ru.mamykin.exchange.core.mvp

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.mamykin.exchange.core.rx.SchedulersProvider

abstract class BaseViewModel(
    protected open val schedulersProvider: SchedulersProvider
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
    }

    protected fun <T> Observable<T>.ioToMain(): Observable<T> =
        subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.mainThread())

    protected fun Disposable.unsubscribeOnDestroy(): Disposable {
        compositeDisposable.add(this)
        return this
    }
}