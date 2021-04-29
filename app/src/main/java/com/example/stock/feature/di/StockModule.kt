package com.example.stock.feature.di

import com.example.stock.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [MainModule::class]
)
object StockModule

@Module
abstract class MainModule {
    @ContributesAndroidInjector
    abstract fun providesMainActivity():MainActivity
}