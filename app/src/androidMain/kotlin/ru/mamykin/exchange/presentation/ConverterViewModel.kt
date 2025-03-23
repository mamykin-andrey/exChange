package ru.mamykin.exchange.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.mamykin.exchange.R
import ru.mamykin.exchange.domain.ConverterInteractor
import ru.mamykin.exchange.domain.RateEntity
import javax.inject.Inject

internal class ConverterViewModel @Inject constructor(
    private val interactor: ConverterInteractor,
    private val viewDataMapper: RateViewDataMapper,
) : ViewModel() {

    private var ratesJob: Job? = null
    private var currentCurrency: CurrentCurrencyRate? = null

    val isLoading = MutableLiveData(true)
    val rates = MutableLiveData<List<CurrencyRateViewData>>()
    val error = MutableLiveData<Int>()
    val currentRateChanged = MutableLiveData<Unit>()

    fun startRatesLoading() {
        loadRates(null, true)
    }

    fun stopRatesLoading() {
        ratesJob?.cancel()
        ratesJob = null
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
        ratesJob?.cancel()
        ratesJob = interactor.getRates(currentCurrency, currencyChanged)
            .onEach {
                isLoading.postValue(false)
                onRatesLoaded(it, currentCurrency, currencyChanged)
            }.launchIn(viewModelScope)
    }

    private fun onRatesLoaded(
        result: Result<List<RateEntity>>,
        currentCurrency: CurrentCurrencyRate?,
        currencyChanged: Boolean,
    ) {
        result.fold(
            onSuccess = {
                rates.postValue(viewDataMapper.transform(it, currentCurrency))
                if (currencyChanged) {
                    currentRateChanged.postValue(Unit)
                }
            },
            onFailure = {
                error.postValue(R.string.error_network)
                ratesJob?.cancel() // let the user retry when needed
            },
        )
    }
}