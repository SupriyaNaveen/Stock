package com.example.stock.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

// https://developer.android.com/training/data-storage/room/accessing-data
@Dao
interface StockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStocks(vararg stocks: StockEntity)
}