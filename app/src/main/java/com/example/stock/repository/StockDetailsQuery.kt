package com.example.stock.repository

import io.reactivex.Observable

class StockDetailsQuery(
    private val database: AppDatabase
) : (String) -> Observable<StockDetails> {
    override fun invoke(symbol: String): Observable<StockDetails> =
        database
            .connectSingle
            .flatMapObservable { response ->
                response
                    .stockDao()
                    .stockDetails(symbol)
                    .map {
                        StockDetails(
                            symbol = it.stockEntity.symbol,
                            changesPercentage = it.stockProfileEntity.changesPercentage,
                            image = it.stockProfileEntity.image,
                            changes = it.stockProfileEntity.changes.toString(),
                            industry = it.stockProfileEntity.industry,
                            lastDiv = it.stockProfileEntity.lastDiv,
                            sector = it.stockProfileEntity.sector,
                            name = it.stockEntity.name
                        )
                    }
            }
}