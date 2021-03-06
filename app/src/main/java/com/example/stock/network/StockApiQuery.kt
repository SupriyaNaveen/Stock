package com.example.stock.network

import android.util.Log
import com.example.stock.repository.AppDatabase
import com.example.stock.repository.entities.StockEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

// TODO: Write this block in Work manager
class StockApiQuery(
    private val api: StockApi,
    private val database: AppDatabase,
    private val stockProfileApiQuery: StockProfileApiQuery
) : () -> Unit {
    override fun invoke() {
        CoroutineScope(Dispatchers.IO)
            .launch {
                try {
                    val existingStocks = database.connect.stockDao().loadFavoriteStocks()
                    api
                        .stocks()
                        .let { stockResponse ->
                            stockProfileApiQuery(stockResponse.symbolsList)
                            val stocks = stockResponse
                                .symbolsList
                                .map { stock ->
                                    StockEntity(
                                        symbol = stock.symbol,
                                        price = stock.price,
                                        exchange = stock.exchange,
                                        name = stock.name,
                                        isFavourite = existingStocks
                                            .firstOrNull { it.symbol == stock.symbol }
                                            ?.isFavourite
                                            ?: false
                                    )
                                }
                            database
                                .connect
                                .stockDao()
                                .insertStocks(*stocks.toTypedArray())
                        }
                } catch (e: Exception) {
                    Log.e(StockApiQuery::class.java.simpleName, e.toString())
                }

            }
    }
}