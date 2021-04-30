package com.example.stock.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable

// https://developer.android.com/training/data-storage/room/accessing-data
@Dao
interface StockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStocks(vararg stocks: StockEntity)

    @Query("SELECT * FROM `stock`")
    fun loadStocks(): Observable<List<StockEntity>>
}