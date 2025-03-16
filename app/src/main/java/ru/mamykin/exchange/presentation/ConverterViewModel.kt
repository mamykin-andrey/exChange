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
    private var currentCurrencyCode: String? = null
    private var currentAmount: Float? = null

    val isLoading = MutableLiveData(true)
    val rates = MutableLiveData<List<RateViewData>>()
    val error = MutableLiveData<Int>()
    val currentRateChanged = MutableLiveData<Unit>()

    override fun onCleared() {
        compositeDisposable.clear()
    }

    fun startRatesLoading() {
        loadRates(null, null, true)
    }

    fun stopRatesLoading() {
        ratesDisposable?.dispose()
    }

    fun onCurrencyOrAmountChanged(currencyCode: String, amount: Float) {
        if (currencyCode == currentCurrencyCode && amount == currentAmount) return

        val currencyChanged = currencyCode != currentCurrencyCode
        this.currentCurrencyCode = currencyCode
        this.currentAmount = amount
        loadRates(currentCurrencyCode, currentAmount, currencyChanged)
    }

    private fun loadRates(
        currentCurrency: String?,
        currentCurrencyAmount: Float?,
        currencyChanged: Boolean,
    ) {
        ratesDisposable?.dispose()
        ratesDisposable = interactor.getRates(currentCurrency, currentCurrencyAmount, currencyChanged)
            .subscribeOn(ioScheduler)
            .observeOn(mainScheduler)
            .doOnEach { isLoading.value = false }
            .subscribe { onRatesLoaded(it, currencyChanged) }
            .unsubscribeOnDestroy()
    }

    private fun onRatesLoaded(result: Result<List<RateEntity>>, currencyChanged: Boolean) {
        result.fold(
            onSuccess = {
                rates.value = mapViewData.transform(it, currentCurrencyCode)
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