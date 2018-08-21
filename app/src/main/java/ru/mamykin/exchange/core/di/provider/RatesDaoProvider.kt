package ru.mamykin.exchange.core.di.provider

import android.content.Context
import androidx.room.Room
import ru.mamykin.exchange.data.database.RatesDao
import ru.mamykin.exchange.data.database.RatesDatabase
import javax.inject.Inject
import javax.inject.Provider

class RatesDaoProvider @Inject constructor(
        private val context: Context
) : Provider<RatesDao> {
    override fun get(): RatesDao {
        val db = Room.databaseBuilder(context, RatesDatabase::class.java, "rates").build()
        return db.getRatesDao()
    }
}