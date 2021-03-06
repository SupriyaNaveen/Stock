package com.example.stock.feature

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stock.BuildConfig
import com.example.stock.network.StockApi
import com.example.stock.network.StockPriceApiQuery
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
    private val stockPriceApiQuery: StockPriceApiQuery,
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
    }

    fun refreshPrice(symbol: String) =
        CoroutineScope(dispatchers.io)
            .launch {
                while (isActive) {
                    try {
                        val value = stockPriceApiQuery.invoke(symbol) ?: return@launch
                        stockPrice.postValue(value)
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