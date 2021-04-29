package com.example.stock.network

import kotlinx.serialization.Serializable

@Serializable
data class Stock(
    val symbol: String,
    val name: String,
    val price: String,
    val exchange: String
)