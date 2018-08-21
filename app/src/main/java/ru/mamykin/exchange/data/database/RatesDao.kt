package ru.mamykin.exchange.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.mamykin.exchange.data.database.model.RateDbEntity
import ru.mamykin.exchange.data.database.model.RateListDbEntity

@Dao
interface RatesDao {

    @Insert
    fun insertRateList(rateList: RateListDbEntity)

    @Insert
    fun insertRates(rates: List<RateDbEntity>)

    @Query("SELECT * FROM ratelistdbentity LIMIT 1")
    fun getRateList(): RateListDbEntity

    // TODO: Union
    @Query("SELECT * FROM ratedbentity ORDER BY code")
    fun getRates(): List<RateDbEntity>

    @Query("SELECT COUNT(*) FROM ratelistdbentity")
    fun rowsCount(): Int

    @Query("DELETE FROM ratelistdbentity")
    fun clearRateList()

    @Query("DELETE FROM ratedbentity")
    fun clearRates()
}