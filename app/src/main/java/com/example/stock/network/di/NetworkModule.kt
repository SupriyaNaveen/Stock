package com.example.stock.network.di

import com.example.stock.network.StockApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import java.util.*
import javax.inject.Singleton

// https://github.com/Kotlin/kotlinx.serialization/
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @[Provides Singleton]
    fun providesConfigurationInterceptor(): ConfigurationInterceptor =
        ConfigurationInterceptor()

    @[Provides Singleton]
    fun providesOkHttpClient(
        interceptor: ConfigurationInterceptor
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()

    @[Provides Singleton]
    fun providesRetrofitBuilder(
        okHttpClient: OkHttpClient
    ): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .baseUrl("https://financialmodelingprep.com")
            .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
            .build()
    }

    @[Provides Singleton]
    fun providesStockApi(retrofit: Retrofit): StockApi =
        retrofit.create(StockApi::class.java)
}

class ConfigurationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest =
            chain
                .request()
                .newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Accept-Language", Locale.getDefault().toLanguageTag())
                .build()

        return chain.proceed(newRequest)
    }
}
