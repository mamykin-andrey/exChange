package ru.mamykin.exchange.data.database.model.mapper

import ru.mamykin.exchange.data.database.model.RateDbEntity
import ru.mamykin.exchange.data.database.model.RateListDbEntity
import ru.mamykin.exchange.domain.entity.Rate
import ru.mamykin.exchange.domain.entity.RateList
import javax.inject.Inject

class RateListToRateListDbEntityMapper @Inject constructor() {

    fun transform(rateList: RateList): RateListDbEntity = RateListDbEntity().apply {
        base = rateList.base
        date = rateList.date
        rates = ratesToRatesDbEntities(rateList.rates)
    }

    fun reverse(dbEntity: RateListDbEntity): RateList = RateList(
            dbEntity.base,
            dbEntity.date,
            ratesDbEntitiesToRates(dbEntity.rates)
    )

    private fun ratesToRatesDbEntities(rates: List<Rate>): List<RateDbEntity> {
        return rates.map {
            RateDbEntity().apply {
                code = it.code
                amount = it.amount
            }
        }
    }

    private fun ratesDbEntitiesToRates(dbEntities: List<RateDbEntity>): List<Rate> {
        return dbEntities.map {
            Rate(it.code, it.amount)
        }
    }
}