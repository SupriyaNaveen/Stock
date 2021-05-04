package com.example.stock.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.stock.repository.dao.StockDao
import com.example.stock.repository.entities.StockEntity
import com.example.stock.repository.entities.StockProfileEntity

// https://github.com/android/architecture-components-samples/tree/main/BasicRxJavaSampleKotlin/app/src/main/java/com/example/android/observability/persistence
@Database(entities = [StockEntity::class, StockProfileEntity::class], version = 2)
abstract class StockDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDao

    class Factory(private val context: Context) {

        private val migration1_2 = object: Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE `stock` ADD COLUMN isFavourite INTEGER NOT NULL DEFAULT 0"
                )
            }
        }
        private val migrations: List<Migration> = listOf(migration1_2)

        fun newInstance() = Room.databaseBuilder(
            context,
            StockDatabase::class.java,
            StockDatabase::class.java.name
        )
            .addMigrations(*migrations.toTypedArray())
            .build()
    }
}