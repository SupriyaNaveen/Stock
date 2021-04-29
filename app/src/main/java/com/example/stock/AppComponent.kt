package com.example.stock

import com.example.stock.feature.di.StockModule
import com.example.stock.network.di.NetworkModule
import com.example.stock.repository.di.DatabaseModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        StockModule::class
    ]
)
interface AppComponent {
    fun inject(application: App)
}