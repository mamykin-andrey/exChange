package ru.mamykin.exchange.data

import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import ru.mamykin.exchange.data.network.RateListResponse
import ru.mamykin.exchange.data.network.RatesApi
import java.util.Date

class RatesRepositoryTest {

    private val ratesApi: RatesApi = mock()
    private val rates1 = listOf(
        "RUB" to 100f,
        "USD" to 1f,
    )
    private val rates2 = listOf(
        "RUB" to 100f,
        "USD" to 0.9f,
    )
    private val ratesResponse1 = RateListResponse("EUR", Date(), rates1.toMap())
    private val ratesResponse2 = RateListResponse("EUR", Date(), rates2.toMap())
    private val repository = RatesRepository(ratesApi)

    @Before
    fun setUp() {
        whenever(ratesApi.getRates(any(), any(), any()))
            .thenReturn(Single.just(ratesResponse1), Single.just(ratesResponse2))
    }

    @Test
    fun `return data from remote when cache is empty`() {
        val testObserver = repository.getRates(false).test()

        testObserver.assertNoErrors().assertValue {
            it.size == rates1.size
                && it.first().let { it.code to it.amount } == rates1.first()
                && it[1].let { it.code to it.amount } == rates1[1]
        }
    }

    @Test
    fun `return data from cache when force is false`() {
        // updates cache and uses rates/response 1
        repository.getRates(false).test().assertComplete()

        val testObserver = repository.getRates(false).test()

        testObserver.assertNoErrors().assertValue {
            it.size == rates1.size
                && it.first().let { it.code to it.amount } == rates1.first()
                && it[1].let { it.code to it.amount } == rates1[1]
        }
    }

    @Test
    fun `return data from remote when force is true`() {
        // updates cache and uses rates/response 1
        repository.getRates(false).test().assertComplete()

        val testObserver = repository.getRates(true).test()

        testObserver.assertNoErrors().assertValue {
            it.size == rates2.size
                && it.first().let { it.code to it.amount } == rates2.first()
                && it[1].let { it.code to it.amount } == rates2[1]
        }
    }
}