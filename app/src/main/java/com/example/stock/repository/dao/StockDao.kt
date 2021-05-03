package com.example.stock.repository.dao

import androidx.annotation.WorkerThread
import androidx.room.*
import com.example.stock.repository.entities.StockEntity
import com.example.stock.repository.entities.StockProfileData
import com.example.stock.repository.entities.StockProfileEntity
import io.reactivex.Completable
import io.reactivex.Observable

// https://developer.android.com/training/data-storage/room/accessing-data
@Dao
interface StockDao {
    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStocks(vararg stocks: StockEntity)

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStockProfile(vararg stockProfile: StockProfileEntity)

    @Transaction
    @Query("SELECT * FROM `stock`, `stockProfile` WHERE `stock`.symbol = `stockProfile`.symbol")
    fun loadStocks(): Observable<List<StockProfileData>>
}