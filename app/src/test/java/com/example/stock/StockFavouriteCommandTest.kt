package com.example.stock

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.stock.repository.AppDatabase
import com.example.stock.repository.StockDatabase
import com.example.stock.repository.StockFavouriteCommand
import com.example.stock.repository.entities.StockEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(
    sdk = [23],
    application = Application::class
)
class StockFavouriteCommandTest {
    private val dispatcher = TestCoroutineDispatcher()
    private val scope = TestCoroutineScope(dispatcher)
    private val context: Context = ApplicationProvider.getApplicationContext()
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val database = Room
        .inMemoryDatabaseBuilder(
            context,
            StockDatabase::class.java
        )
        .allowMainThreadQueries()
        .build()

    @Test
    fun givenDatabase_whenSavingFavourite_thenVerifyTheFavData() {
        scope.runBlockingTest {
            // Arrange
            val stockFavouriteCommand = StockFavouriteCommand(database = AppDatabase { database })
            val stocks = listOf(
                createStock("symbol1"),
                createStock("symbol2"),
                createStock("symbol3")
            )
            database.stockDao().insertStocks(*stocks.toTypedArray())

            // Act
            stockFavouriteCommand.invoke("symbol2").blockingGet()

            // Assert
            val actual = database.stockDao().loadFavoriteStocks()
            assert(actual.size == 1)
            assert(actual[0].isFavourite)
        }
    }

    @Test
    fun givenDatabase_whenSavingAllAsFavourite_thenVerifyTheFavData() {
        scope.runBlockingTest {
            // Arrange
            val stockFavouriteCommand = StockFavouriteCommand(database = AppDatabase { database })
            val stocks = listOf(
                createStock("symbol1"),
                createStock("symbol2"),
                createStock("symbol3")
            )
            database.stockDao().insertStocks(*stocks.toTypedArray())

            // Act
            stockFavouriteCommand.invoke("symbol1").blockingGet()
            stockFavouriteCommand.invoke("symbol2").blockingGet()
            stockFavouriteCommand.invoke("symbol3").blockingGet()

            // Assert
            val actual = database.stockDao().loadFavoriteStocks()
            assert(actual.size == 3)
            assert(actual[0].isFavourite)
            assert(actual[1].isFavourite)
            assert(actual[2].isFavourite)
        }
    }

    private fun createStock(symbol: String) = StockEntity(
        symbol = symbol,
        name = "name",
        price = 10.4,
        exchange = "exchange",
        isFavourite = false
    )
}