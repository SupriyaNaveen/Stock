package com.example.stock.repository

import androidx.annotation.WorkerThread
import io.reactivex.Single
import javax.inject.Provider

class AppDatabase(
    private val database: Provider<StockDatabase>
) {

    val connect: StockDatabase
        @WorkerThread
        get() = database.get()

    val connectSingle: Single<StockDatabase>
        get() = Single.just(connect)
}