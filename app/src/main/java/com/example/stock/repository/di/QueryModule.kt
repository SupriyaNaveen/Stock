package com.example.stock.repository.di

import com.example.stock.feature.AppDispatchers
import com.example.stock.repository.AppDatabase
import com.example.stock.repository.StockDetailsQuery
import com.example.stock.repository.StockFavouriteCommand
import com.example.stock.repository.StockQuery
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object QueryModule {
    @Provides
    fun providesStockQuery(database: AppDatabase): StockQuery =
        StockQuery(database = database)

    @Provides
    fun providesStockDetailsQuery(database: AppDatabase): StockDetailsQuery =
        StockDetailsQuery(database = database)

    @Provides
    fun providesStockFavouriteCommand(database: AppDatabase): StockFavouriteCommand =
        StockFavouriteCommand(database = database)

    @Provides
    fun providesCoroutineDispatchers(): AppDispatchers = AppDispatchers()
}