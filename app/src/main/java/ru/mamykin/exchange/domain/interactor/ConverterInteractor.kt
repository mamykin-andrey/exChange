package ru.mamykin.exchange.domain.interactor

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ru.mamykin.exchange.data.repository.RatesRepository
import ru.mamykin.exchange.domain.entity.RateList
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