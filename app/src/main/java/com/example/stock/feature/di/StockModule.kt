package com.example.stock.feature.di

import com.example.stock.feature.StockAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object StockModule {
    @Provides
    fun providesStockAdapter(): StockAdapter =
        StockAdapter()
}