package ru.mamykin.exchange.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.mamykin.exchange.R
import ru.mamykin.exchange.domain.ConverterInteractor
import ru.mamykin.exchange.domain.RateEntity
import javax.inject.Inject

internal class ConverterViewModel(
    private val interactor: ConverterInteractor,
    private val ioScheduler: Scheduler,
    private val mainScheduler: Scheduler,
    private val mapViewData: RateViewDataMapper
) : ViewModel() {

    @Inject
    constructor(
        interactor: ConverterInteractor,
        mapViewData: RateViewDataMapper
    ) : this(interactor, Schedulers.io(), AndroidSchedulers.mainThread(), mapViewData)

    private val compositeDisposable = CompositeDisposable()
    private var ratesDisposable: Disposable? = null
    private var currentCurrency: CurrentCurrencyRate? = null

    val isLoading = MutableLiveData(true)
    val rates = MutableLiveData<List<CurrencyRateViewData>>()
    val error = MutableLiveData<Int>()
    val currentRateChanged = MutableLiveData<Unit>()

    override fun onCleared() {
        compositeDisposable.clear()
    }

    fun startRatesLoading() {
        loadRates(null, true)
    }

    fun stopRatesLoading() {
        ratesDisposable?.dispose()
    }

    fun onCurrencyOrAmountChanged(currencyRate: CurrentCurrencyRate) {
        currencyRate.amountStr.toFloatOrNull() ?: return
        if (currencyRate.code == currentCurrency?.code && currencyRate.amountStr == currentCurrency?.amountStr) return

        val currencyChanged = currencyRate.code != currentCurrency?.code
        this.currentCurrency = currencyRate
        loadRates(currencyRate, currencyChanged)
    }

    private fun loadRates(
        currentCurrency: CurrentCurrencyRate?,
        currencyChanged: Boolean,
    ) {
        ratesDisposable?.dispose()
        ratesDisposable = interactor.getRates(currentCurrency, currencyChanged)
            .subscribeOn(ioScheduler)
            .observeOn(mainScheduler)
            .doOnEach { isLoading.value = false }
            .subscribe { onRatesLoaded(it, currentCurrency, currencyChanged) }
            .unsubscribeOnDestroy()
    }

    private fun onRatesLoaded(
        result: Result<List<RateEntity>>,
        currentCurrency: CurrentCurrencyRate?,
        currencyChanged: Boolean,
    ) {
        result.fold(
            onSuccess = {
                rates.value = mapViewData.transform(it, currentCurrency)
                if (currencyChanged) {
                    currentRateChanged.value = Unit
                }
            },
            onFailure = {
                error.value = R.string.error_network
                ratesDisposable?.dispose()
            },
        )
    }

    private fun Disposable.unsubscribeOnDestroy(): Disposable {
        compositeDisposable.add(this)
        return this
    }
}