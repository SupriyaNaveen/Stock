package com.example.stock.network

import android.util.Log
import com.example.stock.repository.AppDatabase
import com.example.stock.repository.entities.StockProfileEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class StockProfileApiQuery(
    private val api: StockApi,
    private val database: AppDatabase
) : (List<Stock>) -> Unit {
    override fun invoke(stocks: List<Stock>) {
        CoroutineScope(Dispatchers.IO)
            .launch {
                stocks
                    .map { it.symbol }
                    .map { symbol ->
                        try {
                            api
                                .stocksProfile(symbol)
                                .let {
                                    database
                                        .connect
                                        .stockDao()
                                        .insertStockProfile(
                                            StockProfileEntity(
                                                symbol = it.symbol,
                                                changesPercentage = it.profile.changesPercentage,
                                                image = it.profile.image
                                            )
                                        )
                                }
                        } catch (e: Exception) {
                            Log.e(StockProfileApiQuery::class.java.simpleName, e.toString())
                        }
                    }
            }
    }
}