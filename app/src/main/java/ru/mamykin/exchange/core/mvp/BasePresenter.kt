package ru.mamykin.exchange.core.mvp

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import moxy.MvpView
import ru.mamykin.exchange.core.rx.SchedulersProvider

abstract class BasePresenter<V : MvpView>(
    protected open val schedulersProvider: SchedulersProvider
) : MvpPresenter<V>() {

    private val compositeDisposable = CompositeDisposable()

    fun Disposable.unsubscribeOnDestroy(): Disposable {
        compositeDisposable.add(this)
        return this
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    protected fun <T> Observable<T>.ioToMain(): Observable<T> =
        subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.mainThread())
}