package com.example.stock

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.stock.repository.*
import com.example.stock.repository.entities.StockEntity
import com.example.stock.repository.entities.StockProfileEntity
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
class StockDetailsQueryTest {
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
    fun givenDatabaseWithStocksAndItsProfile_whenFetchingTheStockDetails_thenVerifyTheStockDetailsData() {
        scope.runBlockingTest {
            // Arrange
            val stockDetailsQuery = StockDetailsQuery(database = AppDatabase { database })
            val stock1 = createStock("symbol1")
            val stock2 = createStock("symbol2")
            val stock3 = createStock("symbol3")
            val stockProfile = createStockProfile(stock2.symbol)
            val expectedStockDetails = StockDetails(
                symbol = stock2.symbol,
                changesPercentage = stockProfile.changesPercentage,
                image = stockProfile.image,
                changes = stockProfile.changes.toString(),
                industry = stockProfile.industry,
                lastDiv = stockProfile.lastDiv,
                sector = stockProfile.sector,
                name = stock2.name
            )
            database.stockDao().insertStocks(*(listOf(stock1, stock2, stock3)).toTypedArray())
            database.stockDao().insertStockProfile((stockProfile))

            // Act
            val actual = stockDetailsQuery.invoke(stock2.symbol).test()

            // Assert
            actual.assertValue(expectedStockDetails)
        }
    }

    @Test
    fun givenDatabaseWithStocksOnly_whenFetchingTheStockDetails_thenNoStockDetailsReceived() {
        scope.runBlockingTest {
            // Arrange
            val stockDetailsQuery = StockDetailsQuery(database = AppDatabase { database })
            val stock1 = createStock("symbol1")
            val stock2 = createStock("symbol2")
            val stock3 = createStock("symbol3")
            database.stockDao().insertStocks(*(listOf(stock1, stock2, stock3)).toTypedArray())

            // Act
            val actual = stockDetailsQuery.invoke(stock2.symbol).test()

            // Assert
            actual.assertEmpty()
        }
    }

    @Test
    fun givenDatabaseWithStocksProfileOnly_whenFetchingTheStockDetails_thenNoStockDetailsReceived() {
        scope.runBlockingTest {
            // Arrange
            val stockDetailsQuery = StockDetailsQuery(database = AppDatabase { database })
            val stockProfile = createStockProfile("symbol")
            database.stockDao().insertStockProfile((stockProfile))

            // Act
            val actual = stockDetailsQuery.invoke("symbol").test()

            // Assert
            actual.assertEmpty()
        }
    }

    private fun createStock(symbol: String) = StockEntity(
        symbol = symbol,
        name = "name",
        price = 10.4,
        exchange = "exchange",
        isFavourite = false
    )


    private fun createStockProfile(symbol: String) = StockProfileEntity(
        symbol = symbol,
        changesPercentage = "changesPercentage",
        image = "image",
        changes = 100.98,
        lastDiv = "lastDiv",
        sector = "sector",
        industry = "industry"
    )
}