package com.example.stock.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class StockQuery(
    private val database: AppDatabase
) : () -> Flow<PagingData<StockProfileData>> {
    override fun invoke(): Flow<PagingData<StockProfileData>> =
        Pager(
            PagingConfig(
                pageSize = 20,
                prefetchDistance = 20,
                maxSize = 100
            )
        )
        {
            database.connect.stockDao().loadStocks()
        }
            .flow
}