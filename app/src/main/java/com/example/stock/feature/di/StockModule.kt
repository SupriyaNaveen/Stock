package com.example.stock.feature.di

import com.example.stock.MainActivity
import com.example.stock.feature.StockAdapter
import com.example.stock.feature.StockFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [MainModule::class]
)
object StockModule {
    @Provides
    fun providesStockAdapter(): StockAdapter =
        StockAdapter()
}

@Module
abstract class MainModule {
    @ContributesAndroidInjector
    abstract fun providesMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun providesStockFragment(): StockFragment
}