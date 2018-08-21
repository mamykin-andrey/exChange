package ru.mamykin.exchange.data.database.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity
class RateListDbEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var base: String = ""
    var date: Date = Date()
    @Ignore
    var rates: List<RateDbEntity> = listOf()
}