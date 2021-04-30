package com.example.stock.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stock.repository.entities.StockEntity
import com.example.stock.repository.entities.StockProfileEntity
import io.reactivex.Observable

// https://developer.android.com/training/data-storage/room/accessing-data
@Dao
interface StockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStocks(vararg stocks: StockEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStockProfile(stockProfile: StockProfileEntity)

    @Query("SELECT * FROM `stock`")
    fun loadStocks(): Observable<List<StockEntity>>
}