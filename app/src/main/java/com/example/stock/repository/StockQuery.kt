package com.example.stock.repository

import io.reactivex.Observable

class StockQuery(
    private val database: AppDatabase
) : () -> Observable<List<StockProfileData>> {
    override fun invoke(): Observable<List<StockProfileData>> =
        database
            .connectSingle
            .flatMapObservable {
                it.stockDao().loadStocks()
            }
}