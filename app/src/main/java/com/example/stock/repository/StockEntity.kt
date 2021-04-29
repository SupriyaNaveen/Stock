package com.example.stock.repository

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "stock")
data class StockEntity(
    @PrimaryKey val symbol: String,
    val name: String,
    val price: BigDecimal,
    val exchange: String
)