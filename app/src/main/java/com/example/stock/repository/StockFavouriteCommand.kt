package com.example.stock.repository

import io.reactivex.Single

class StockFavouriteCommand(
    private val database: AppDatabase
) : (String) -> Single<Unit> {
    override fun invoke(symbol: String): Single<Unit> =
        database
            .connectSingle
            .flatMap { connection ->
                connection.stockDao().saveFavourite(symbol = symbol)
            }
}