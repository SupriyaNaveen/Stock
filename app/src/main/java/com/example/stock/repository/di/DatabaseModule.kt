package com.example.stock.repository.di

import android.app.Application
import com.example.stock.repository.AppDatabase
import com.example.stock.repository.StockDatabase
import com.example.stock.repository.StockQuery
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider

@Module
@InstallIn(SingletonComponent::class)
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

    @Provides
    fun providesStockQuery(database: AppDatabase): StockQuery =
        StockQuery(database = database)
}