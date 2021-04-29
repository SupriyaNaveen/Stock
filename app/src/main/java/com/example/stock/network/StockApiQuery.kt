package com.example.stock.network

import com.example.stock.repository.AppDatabase
import com.example.stock.repository.StockEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StockApiQuery(
    private val api: StockApi,
    private val database: AppDatabase
) : () -> Unit {
    override fun invoke() {
        CoroutineScope(Dispatchers.IO)
            .launch {
                val stocks = api
                    .stocks()
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
    }
}