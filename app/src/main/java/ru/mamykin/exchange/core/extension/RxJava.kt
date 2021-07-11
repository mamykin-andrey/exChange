package ru.mamykin.exchange.core.extension

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.ioToMain(): Observable<T> =
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())