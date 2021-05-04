package com.example.stock.repository.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock")
data class StockEntity(
    @PrimaryKey val symbol: String,
    val name: String,
    val price: Double,
    val exchange: String,
    val isFavourite: Boolean
)