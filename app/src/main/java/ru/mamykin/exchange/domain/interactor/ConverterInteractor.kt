package ru.mamykin.exchange.domain.interactor

import ru.mamykin.exchange.data.repository.RatesRepository
import ru.mamykin.exchange.domain.entity.RateList
import rx.Observable
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ConverterInteractor @Inject constructor(
        private val ratesRepository: RatesRepository
) {
    fun getRates(baseCurrency: String): Observable<RateList> {
        return Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                .flatMapSingle { ratesRepository.getRates(baseCurrency) }
                .retry()
    }
}