package com.example.stock.repository.di

import android.app.Application
import com.example.stock.feature.AppDispatchers
import com.example.stock.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @[Provides Singleton]
    fun providesStockDatabaseFactory(app: Application): StockDatabase.Factory =
        StockDatabase.Factory(app)

    @[Provides Singleton]
    fun providesStockDatabase(factory: StockDatabase.Factory): StockDatabase =
        factory.newInstance()

    @[Provides Singleton]
    fun providesAppDatabase(database: Provider<StockDatabase>): AppDatabase =
        AppDatabase(database = database)
}