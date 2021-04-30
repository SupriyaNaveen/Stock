package com.example.stock.network

import android.util.Log
import com.example.stock.repository.AppDatabase
import com.example.stock.repository.entities.StockEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class StockApiQuery(
    private val api: StockApi,
    private val database: AppDatabase,
    private val stockProfileApiQuery: StockProfileApiQuery
) : () -> Unit {
    override fun invoke() {
        CoroutineScope(Dispatchers.IO)
            .launch {
                try {
                    api
                        .stocks()
                        .let { stockResponse ->
                            stockProfileApiQuery(stockResponse.symbolsList)
                            val stocks = stockResponse
                                .symbolsList
                                .map {
                                    StockEntity(
                                        symbol = it.symbol,
                                        price = it.price,
                                        exchange = it.exchange,
                                        name = it.name
                                    )
                                }
                            database
                                .connect
                                .stockDao()
                                .insertStocks(*stocks.toTypedArray())
                        }
                }catch (e: Exception) {
                    Log.e(StockApiQuery::class.java.simpleName, e.toString())
                }

            }
    }
}