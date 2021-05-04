package com.example.stock

import android.app.Application
import android.content.Context
import android.os.Looper.getMainLooper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.stock.feature.StockAdapter
import com.example.stock.repository.*
import com.example.stock.repository.entities.StockEntity
import com.example.stock.repository.entities.StockProfileEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(
    sdk = [23],
    application = Application::class
)
class StockQueryTest {
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
    fun givenDatabaseWithStocksAndItsProfile_whenLoadingTheStockList_thenVerifyTheStockList() {
        scope.runBlockingTest {
            // Arrange
            val stockQuery = StockQuery(database = AppDatabase { database })
            val stock1 = createStock("symbol1")
            val stock2 = createStock("symbol2")
            val stock3 = createStock("symbol3")
            val stockProfile1 = createStockProfile(stock1.symbol)
            val stockProfile2 = createStockProfile(stock2.symbol)
            val stockProfile3 = createStockProfile(stock2.symbol)
            database.stockDao().insertStocks(*(listOf(stock1, stock2, stock3)).toTypedArray())
            database.stockDao().insertStockProfile(
                *(listOf(stockProfile1, stockProfile2, stockProfile3)).toTypedArray()
            )
//            val expectedStockData = listOf(
//                StockProfileData(stock1, stockProfile1),
//                StockProfileData(stock2, stockProfile2),
//                StockProfileData(stock3, stockProfile3)
//            )
            val adapter = StockAdapter()

            // Act
            val job = launch(Dispatchers.IO) {
                val actual = stockQuery.invoke()
                actual.collect { adapter.submitData(it) }
            }

            // Assert
            shadowOf(getMainLooper()).idle()
            val snapshotData = adapter.snapshot()
            assert(snapshotData.items.size == 1)
            job.cancel()
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