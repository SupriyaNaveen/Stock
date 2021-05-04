package com.example.stock.feature

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stock.BuildConfig
import com.example.stock.network.StockApi
import com.example.stock.network.StockPriceResponse
import com.example.stock.repository.StockDetails
import com.example.stock.repository.StockDetailsQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class StockDetailsViewModel @Inject constructor(
    private val stockDetailsQuery: StockDetailsQuery,
    // TODO: View model need not access api reference directly, so wrap the query class
    private val api: StockApi,
    private val dispatchers: AppDispatchers
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private var isActive: Boolean = true
    private val stockDetails: MutableLiveData<StockDetails> by lazy {
        MutableLiveData<StockDetails>()
    }
    private val stockPrice: MutableLiveData<StockPriceResponse> by lazy {
        MutableLiveData<StockPriceResponse>()
    }

    fun getStockDetails(): LiveData<StockDetails> {
        return stockDetails
    }

    fun getStockPrice(): LiveData<StockPriceResponse> {
        return stockPrice
    }

    fun loadStockDetails(symbol: String) {
        stockDetailsQuery(symbol)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { stockDetails.postValue(it) },
                {
                    Log.e(StockDetailsViewModel::class.java.simpleName, "Error: $it")
                }
            )
            .addTo(disposables)

        refreshPrice(symbol)
    }

    @VisibleForTesting
    fun refreshPrice(symbol: String) =
        CoroutineScope(dispatchers.io)
            .launch {
                while (isActive) {
                    try {
                        stockPrice.postValue(api.stocksPrice(symbol))
                        delay(TimeUnit.SECONDS.toMillis(BuildConfig.REFRESH_TIME))
                    } catch (e: Exception) {
                        Log.e(StockDetailsViewModel::class.java.simpleName, e.toString())
                    }
                }

            }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
        isActive = false
    }
}