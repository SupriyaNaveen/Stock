package com.example.stock.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.stock.R
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class StockFragment : Fragment() {
    @Inject lateinit var stockAdapter: StockAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.stock_view, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val stockRecyclerView: RecyclerView = view.findViewById(R.id.stocksRecyclerView)
        stockRecyclerView.adapter = stockAdapter

        val dividerItemDecoration = DividerItemDecoration(
            stockRecyclerView.context,
            RecyclerView.VERTICAL
        )
        stockRecyclerView.addItemDecoration(dividerItemDecoration)
    }
}