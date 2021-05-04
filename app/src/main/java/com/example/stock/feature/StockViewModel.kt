package com.example.stock.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.stock.repository.StockFavouriteCommand
import com.example.stock.repository.StockProfileData
import com.example.stock.repository.StockQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    private val stockQuery: StockQuery,
    private val stockFavouriteCommand: StockFavouriteCommand
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    fun loadStocks(): Flow<PagingData<StockProfileData>> =
        stockQuery().cachedIn(viewModelScope)

    fun selectFavourite(symbol: String) {
        stockFavouriteCommand(symbol)
            .subscribeOn(Schedulers.io())
            .subscribe()
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}