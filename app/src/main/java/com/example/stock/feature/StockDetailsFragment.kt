package com.example.stock.feature

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.example.stock.R
import com.example.stock.databinding.StockDetailsViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockDetailsFragment : Fragment() {
    private lateinit var binding: StockDetailsViewBinding
    private val viewModel: StockDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.stock_details_view, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadStockDetails(requireArguments().getString("symbol") ?: "")
        viewModel.getStockDetails().observe(viewLifecycleOwner, {
            binding.stockDetails = it
            binding.companyImageView.load(it.image)
        })
        viewModel.getStockPrice().observe(viewLifecycleOwner, {
            Log.i(StockDetailsFragment::class.java.simpleName, "Updating price to ${it.price}")
            binding.price = it.price.toString()
        })
    }
}