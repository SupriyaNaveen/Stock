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
        // TODO: Add paged network calls here
        // Tried making single network call, using
        // stocks.joinToString(",") { it.symbol }, but request is too lengthy not a good way
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
                                                image = it.profile.image,
                                                changes = it.profile.changes,
                                                lastDiv = it.profile.lastDiv,
                                                sector = it.profile.sector,
                                                industry = it.profile.industry
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