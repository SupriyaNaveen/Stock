package com.example.stock.network.di

import com.example.stock.network.StockApi
import com.example.stock.network.StockApiQuery
import com.example.stock.network.StockProfileApiQuery
import com.example.stock.repository.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ApiModule {

    @Provides
    fun providesStockProfileApiQuery(
        stockApi: StockApi,
        database: AppDatabase
    ): StockProfileApiQuery =
        StockProfileApiQuery(api = stockApi, database = database)

    @Provides
    fun providesStockApiQuery(
        stockApi: StockApi,
        database: AppDatabase,
        stockProfileApiQuery: StockProfileApiQuery
    ): StockApiQuery =
        StockApiQuery(api = stockApi, database = database, stockProfileApiQuery)
}
