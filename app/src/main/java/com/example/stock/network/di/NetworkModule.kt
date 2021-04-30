package com.example.stock.network.di

import com.example.stock.network.StockApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

// https://github.com/Kotlin/kotlinx.serialization/
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun providesRetrofitBuilder(): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        return Retrofit
            .Builder()
            .baseUrl("https://financialmodelingprep.com")
            .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
            .build()
    }

    @Provides
    fun providesStockApi(retrofit: Retrofit): StockApi =
        retrofit.create(StockApi::class.java)
}