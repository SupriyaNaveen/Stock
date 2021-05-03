package com.example.stock.network

import retrofit2.http.GET
import retrofit2.http.Path

// https://square.github.io/retrofit/
// Using the default coroutine adapter
interface StockApi {
    @GET("/api/v3/company/stock/list?apikey=717bc2b6d1c5eabab1bd20bc5ccf7426")
    suspend fun stocks(): StockResponse

    @GET("/api/v3/company/profile/{companyName}?apikey=717bc2b6d1c5eabab1bd20bc5ccf7426")
    suspend fun stocksProfile(
        @Path("companyName") companyName: String
    ): StockProfileResponse

    @GET("/api/v3/stock/real-time-price/{companyName}?apikey=717bc2b6d1c5eabab1bd20bc5ccf7426")
    suspend fun stocksPrice(
        @Path("companyName") companyName: String
    ): StockPriceResponse
}