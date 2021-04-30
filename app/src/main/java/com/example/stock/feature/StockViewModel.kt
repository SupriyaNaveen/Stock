package com.example.stock.feature

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stock.repository.StockEntity
import com.example.stock.repository.StockQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    private val stockQuery: StockQuery
) : ViewModel() {
    private val disposables = CompositeDisposable()

    private val stocks: MutableLiveData<List<StockEntity>> by lazy {
        MutableLiveData<List<StockEntity>>().also {
            loadStocks()
        }
    }

    fun getStocks(): LiveData<List<StockEntity>> {
        return stocks
    }

    private fun loadStocks() {
        stockQuery()
            .subscribeOn(Schedulers.io())
            .subscribe(
                { stocks.postValue(it) },
                {
                    Log.e(StockViewModel::class.java.simpleName, "Error: $it")
                }
            )
            .addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}