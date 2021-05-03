package com.example.stock.repository

import androidx.room.Embedded
import androidx.room.Relation
import com.example.stock.repository.entities.StockEntity
import com.example.stock.repository.entities.StockProfileEntity

data class StockProfileData (
    @Embedded val stockEntity: StockEntity,
    @Relation(
        parentColumn = "symbol",
        entityColumn = "symbol"
    )
    val stockProfileEntity: StockProfileEntity
)

data class StockDetails(
    val symbol: String,
    val changesPercentage: String,
    val image: String,
    val changes: String,
    val industry: String,
    val lastDiv: String,
    val sector: String,
    val name: String
)