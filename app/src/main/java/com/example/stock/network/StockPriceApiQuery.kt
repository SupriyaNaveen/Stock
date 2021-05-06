package com.example.stock.network

import android.util.Log
import com.example.stock.repository.AppDatabase
import com.example.stock.repository.entities.StockEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class StockPriceApiQuery(
    private val api: StockApi
) {
    suspend fun invoke(symbol: String): StockPriceResponse? {
        return try {
            api.stocksPrice(symbol)
        } catch (e: Exception) {
            Log.e(StockPriceApiQuery::class.java.simpleName, e.toString())
            null
        }
    }
}