package com.example.stock.feature

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stock.repository.StockQuery
import com.example.stock.repository.entities.StockProfileData
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

    private val stocks: MutableLiveData<List<StockProfileData>> by lazy {
        MutableLiveData<List<StockProfileData>>().also {
            loadStocks()
        }
    }

    fun getStocks(): LiveData<List<StockProfileData>> {
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