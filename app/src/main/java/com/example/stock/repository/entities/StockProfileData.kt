package com.example.stock.repository.entities

import androidx.room.Embedded
import androidx.room.Relation

data class StockProfileData (
    @Embedded val stockEntity: StockEntity,
    @Relation(
        parentColumn = "symbol",
        entityColumn = "symbol"
    )
    val stockProfileEntity: StockProfileEntity
)