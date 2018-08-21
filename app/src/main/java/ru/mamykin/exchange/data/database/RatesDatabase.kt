package ru.mamykin.exchange.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.mamykin.exchange.data.database.converter.DateTimestampConverter
import ru.mamykin.exchange.data.database.model.RateDbEntity
import ru.mamykin.exchange.data.database.model.RateListDbEntity

@Database(entities = [RateListDbEntity::class, RateDbEntity::class], version = 1)
@TypeConverters(DateTimestampConverter::class)
abstract class RatesDatabase : RoomDatabase() {

    abstract fun getRatesDao(): RatesDao
}