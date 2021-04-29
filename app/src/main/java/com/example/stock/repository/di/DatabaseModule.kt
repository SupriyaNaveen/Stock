package com.example.stock.repository.di

import android.app.Application
import com.example.stock.repository.AppDatabase
import com.example.stock.repository.StockDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
object DatabaseModule {

    @Provides
    fun providesStockDatabaseFactory(app: Application): StockDatabase.Factory =
        StockDatabase.Factory(app)

    @Provides
    fun providesStockDatabase(factory: StockDatabase.Factory): StockDatabase =
        factory.newInstance()

    @Provides
    fun providesAppDatabase(database: Provider<StockDatabase>): AppDatabase =
        AppDatabase(database = database)
}