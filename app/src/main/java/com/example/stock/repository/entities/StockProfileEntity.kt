package com.example.stock.repository.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stockProfile")
data class StockProfileEntity(
    @PrimaryKey val symbol: String,
    val changesPercentage: String,
    val image: String,
    val changes: Double,
    val industry: String,
    val lastDiv: String,
    val sector: String
    ) {
    val isPositive: Boolean
        get() = changesPercentage.contains("+")
}