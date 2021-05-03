package com.example.stock.feature

import android.util.Log
import androidx.lifecycle.*
import com.example.stock.repository.StockDetails
import com.example.stock.repository.StockDetailsQuery
import com.example.stock.repository.StockProfileData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class StockDetailsViewModel @Inject constructor(
    private val stockDetailsQuery: StockDetailsQuery
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val stockDetails: MutableLiveData<StockDetails> by lazy {
        MutableLiveData<StockDetails>()
    }

    fun getStockDetails(): LiveData<StockDetails> {
        return stockDetails
    }

    fun loadStockDetails(symbol:String) {
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

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}