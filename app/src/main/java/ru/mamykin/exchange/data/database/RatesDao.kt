package ru.mamykin.exchange.data.database

import androidx.room.*
import ru.mamykin.exchange.data.database.model.RateDbEntity
import ru.mamykin.exchange.data.database.model.RateListDbEntity

@Dao
interface RatesDao {

    @Insert
    fun insert(rateList: RateListDbEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(rateList: RateListDbEntity): Int

    @Delete
    fun delete(rateList: RateListDbEntity)

    @Query("DELETE FROM ratelistdbentity")
    fun clearAll()

    @Query("SELECT * FROM ratelistdbentity LIMIT 1")
    fun getRateList(): RateListDbEntity

    // TODO: Union
    @Query("SELECT * FROM ratedbentity ORDER BY code")
    fun getRates(): List<RateDbEntity>

    @Query("SELECT COUNT(*) FROM ratelistdbentity")
    fun rowsCount(): Int
}