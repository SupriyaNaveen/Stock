package com.example.stock.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.stock.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class StockFragment : Fragment() {
    @Inject
    lateinit var stockAdapter: StockAdapter
    private val viewModel: StockViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.stock_view, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val stockRecyclerView: RecyclerView = view.findViewById(R.id.stocksRecyclerView)
        stockRecyclerView.adapter = stockAdapter

        lifecycleScope.launch {
                viewModel
                    .loadStocks()
                    .collectLatest { stockAdapter.submitData(it) }
            }

        stockAdapter.onStockSelected = {
            findNavController().navigate(
                StockFragmentDirections.actionStockFragmentToStockDetailsFragment(it)
            )
        }

        stockAdapter.onFavouriteSelected = {
            viewModel.selectFavourite(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stockAdapter.onFavouriteSelected = null
        stockAdapter.onStockSelected = null
    }
}