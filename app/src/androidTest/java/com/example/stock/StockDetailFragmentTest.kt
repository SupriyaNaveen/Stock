package com.example.stock

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import com.example.stock.feature.AppDispatchers
import com.example.stock.feature.StockDetailsFragment
import com.example.stock.feature.di.StockModule
import com.example.stock.network.StockApi
import com.example.stock.network.StockPriceApiQuery
import com.example.stock.repository.StockDetailsQuery
import com.example.stock.repository.di.QueryModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

// Reference https://github.com/android/architecture-samples/tree/dev-hilt/app/src/androidTest/java/com/example/android/architecture/blueprints/todoapp
@HiltAndroidTest
@UninstallModules(QueryModule::class)
class StockDetailFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var stockDetailsQuery: StockDetailsQuery

    @Inject
    lateinit var stockPriceApiQuery: StockPriceApiQuery

    @Inject
    lateinit var appDispatchers: AppDispatchers

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun test() {
        val bundle = bundleOf("symbol" to "SPY")
        launchFragmentInContainer<StockDetailsFragment>(fragmentArgs = bundle)
    }
}