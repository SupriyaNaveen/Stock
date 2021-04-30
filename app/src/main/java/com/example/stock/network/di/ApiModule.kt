package com.example.stock.network.di

import com.example.stock.network.StockApi
import com.example.stock.network.StockApiQuery
import com.example.stock.repository.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ApiModule {

    @Provides
    fun providesStockApiQuery(stockApi: StockApi, database: AppDatabase): StockApiQuery =
        StockApiQuery(stockApi, database)
}