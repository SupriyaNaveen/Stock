package com.example.stock

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.stock.feature.AppDispatchers
import com.example.stock.feature.StockDetailsViewModel
import com.example.stock.network.StockApi
import com.example.stock.network.StockPriceResponse
import com.example.stock.repository.StockDetails
import com.example.stock.repository.StockDetailsQuery
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
@Config(sdk = [23])
class StockDetailsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val dispatcher = TestCoroutineDispatcher()
    private val scope = TestCoroutineScope(dispatcher)

    private fun viewModel(
        stockDetailsQuery: StockDetailsQuery = mockk(relaxed = true),
        stockApi: StockApi = mockk(relaxed = true)
    ): StockDetailsViewModel =
        StockDetailsViewModel(
            stockDetailsQuery = stockDetailsQuery,
            api = stockApi,
            dispatchers = AppDispatchers(io = dispatcher)
        )

    @Test
    fun givenStockDetailsQueryResult_WhenLoadingStockDetails_ThenVerifyTheStockDetails() {
        // Arrange
        val symbol = "symbol"
        val stockDetailsQuery: StockDetailsQuery = mockk(relaxed = true)
        every { stockDetailsQuery.invoke(symbol) } returns Observable.just(
            StockDetails(
                symbol = symbol,
                changesPercentage = "changesPercentage",
                image = "image",
                changes = "100.98",
                lastDiv = "lastDiv",
                sector = "sector",
                industry = "industry",
                name = "name"
            )
        )
        val viewModel = viewModel(stockDetailsQuery = stockDetailsQuery)

        // Act
        viewModel.loadStockDetails(symbol)
        val actual = viewModel.getStockDetails().value

        // Assert
        assert(actual?.lastDiv == "lastDiv")
    }

    @Test
    fun givenStockDetailsQueryResultAsError_WhenLoadingStockDetails_ThenVerifyTheStockDetails() {
        // Arrange
        val symbol = "symbol"
        val stockDetailsQuery: StockDetailsQuery = mockk(relaxed = true)
        every { stockDetailsQuery.invoke(symbol) } returns Observable.error(Throwable())
        val viewModel = viewModel(stockDetailsQuery = stockDetailsQuery)

        // Act
        viewModel.loadStockDetails(symbol)
        val actual = viewModel.getStockDetails().value

        // Assert
        assert(actual == null)
    }

    @Test
    fun givenApiService_WhenLoadingStockPrice_ThenVerifyTheResult() {

        scope.runBlockingTest {
            // Arrange
            val symbol = "symbol"
            val api: StockApi = mockk(relaxed = true)
            coEvery { api.stocksPrice(symbol) } returns StockPriceResponse(symbol, 200.87)
            val viewModel = viewModel(stockApi = api)

            // Act
            val job = viewModel.refreshPrice(symbol)
            val actual = viewModel.getStockPrice().value
            job.cancel()

            // Assert
            assert(actual?.price == 200.87)
        }
    }
}