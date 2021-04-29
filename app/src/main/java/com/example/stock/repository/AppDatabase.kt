package com.example.stock.repository

import androidx.annotation.WorkerThread
import javax.inject.Provider

class AppDatabase(
    private val database: Provider<StockDatabase>
) {

    val connect: StockDatabase
        @WorkerThread
        get() = database.get()
}