package com.example.stock.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// https://github.com/android/architecture-components-samples/tree/main/BasicRxJavaSampleKotlin/app/src/main/java/com/example/android/observability/persistence
@Database(entities = [StockEntity::class], version = 1)
abstract class StockDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDao

    class Factory(private val context: Context) {
        fun newInstance() = Room.databaseBuilder(
            context,
            StockDatabase::class.java,
            StockDatabase::class.java.name
        ).build()
    }
}