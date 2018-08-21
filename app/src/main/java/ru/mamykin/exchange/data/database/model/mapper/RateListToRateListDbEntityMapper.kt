package ru.mamykin.exchange.data.database.model.mapper

import ru.mamykin.exchange.data.database.model.RateDbEntity
import ru.mamykin.exchange.data.database.model.RateListDbEntity
import ru.mamykin.exchange.domain.entity.Rate
import ru.mamykin.exchange.domain.entity.RateList
import javax.inject.Inject

class RateListToRateListDbEntityMapper @Inject constructor() {

    fun transformRateList(rateList: RateList): RateListDbEntity = RateListDbEntity().apply {
        base = rateList.base
        date = rateList.date
        rates = transformRates(rateList.rates)
    }

    fun reverseRateList(dbEntity: RateListDbEntity): RateList = RateList(
            dbEntity.base,
            dbEntity.date,
            reverseRates(dbEntity.rates)
    )

    fun transformRates(rates: List<Rate>): List<RateDbEntity> {
        return rates.map {
            RateDbEntity().apply {
                code = it.code
                amount = it.amount
            }
        }
    }

    fun reverseRates(dbEntities: List<RateDbEntity>): List<Rate> {
        return dbEntities.map {
            Rate(it.code, it.amount)
        }
    }
}