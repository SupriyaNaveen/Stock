package com.example.stock.network

import kotlinx.serialization.Serializable

@Serializable
data class Stock(
    val symbol: String,
    val name: String,
    val price: Double,
    val exchange: String
)

@Serializable
data class StockResponse(
    val symbolsList: List<Stock>
)

@Serializable
data class StockProfileResponse(
    val symbol: String,
    val profile: StockProfile
)

@Serializable
data class StockProfile(
    val changesPercentage: String,
    val image: String,
    val changes: Double,
    val industry: String,
    val lastDiv: String,
    val sector: String
)