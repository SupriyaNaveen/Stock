package com.example.stock.network

import retrofit2.http.GET

// https://square.github.io/retrofit/
interface StockApi {
    @GET("/api/v3/company/stock/list?apikey=717bc2b6d1c5eabab1bd20bc5ccf7426")
    fun stocks() : List<Stock>
}