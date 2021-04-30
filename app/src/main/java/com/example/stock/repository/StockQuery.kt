package com.example.stock.repository

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

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