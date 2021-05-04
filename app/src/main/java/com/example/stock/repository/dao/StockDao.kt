package com.example.stock.repository.dao

import androidx.annotation.WorkerThread
import androidx.paging.PagingSource
import androidx.room.*
import com.example.stock.repository.StockProfileData
import com.example.stock.repository.entities.StockEntity
import com.example.stock.repository.entities.StockProfileEntity
import io.reactivex.Observable
import io.reactivex.Single

// https://developer.android.com/training/data-storage/room/accessing-data
@Dao
interface StockDao {
    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStocks(vararg stocks: StockEntity)


    @WorkerThread
    @Query("SELECT * FROM `stock` WHERE `stock`.isFavourite = 1")
    suspend fun loadFavoriteStocks(): List<StockEntity>

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStockProfile(vararg stockProfile: StockProfileEntity)

    @Transaction
    @Query("SELECT * FROM `stock`, `stockProfile` WHERE `stock`.symbol = `stockProfile`.symbol")
    fun loadStocks(): PagingSource<Int, StockProfileData>

    @Transaction
    @Query("SELECT * FROM `stock`, `stockProfile` WHERE `stock`.symbol = `stockProfile`.symbol AND `stockProfile`.symbol = :symbol")
    fun stockDetails(symbol: String): Observable<StockProfileData>

    @Query("UPDATE `stock` SET `isFavourite` = 1 WHERE `symbol` = :symbol")
    fun saveFavourite(symbol: String): Single<Unit>
}