package com.example.stock.repository

import com.example.stock.repository.entities.StockEntity
import io.reactivex.Observable

class StockQuery(
    private val database: AppDatabase
) : () -> Observable<List<StockEntity>> {
    override fun invoke(): Observable<List<StockEntity>> =
        database
            .connectSingle
            .flatMapObservable {
                it.stockDao().loadStocks()
            }
}