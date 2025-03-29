package ru.mamykin.exchange.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.mamykin.exchange.domain.ConverterInteractor
import ru.mamykin.exchange.domain.RateEntity

class ConverterViewModel(
    private val interactor: ConverterInteractor,
) {
    private val viewModelScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
    private var ratesJob: Job? = null
    private var currentCurrency: CurrentCurrencyRate? = null

    val isLoading = MutableStateFlow(true)
    val rates = MutableStateFlow<CurrencyRatesViewData?>(null)
    val error = MutableStateFlow<String?>(null)
    val currentRateChanged = MutableStateFlow<Unit?>(null)

    fun observeData(onEach: (CurrencyRatesViewData) -> Unit) {
        rates
            .onEach { it?.let { it1 -> onEach(it1) } }
            .launchIn(viewModelScope)
    }

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
        isLoading.value = true
        ratesJob?.cancel()
        ratesJob = interactor.getRates(currentCurrency, currencyChanged).onEach {
            isLoading.value = false
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
                rates.value = CurrencyRatesViewData(it, currentCurrency)
                if (currencyChanged) {
                    currentRateChanged.value = Unit
                }
            },
            onFailure = {
                // error.postValue(R.string.error_network)
                error.value = "Network error, please try again"
                ratesJob?.cancel() // let the user retry when needed
            },
        )
    }
}