package ru.mamykin.exchange.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RateDbEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var code: String = ""
    var amount: Float = 0f
}