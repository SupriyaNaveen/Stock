package com.example.stock.feature

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.stock.R
import com.example.stock.repository.entities.StockProfileData

// TODO: Use paged adapter, try binding for view holder
class StockAdapter() : RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    private var stocks: List<StockProfileData> = emptyList()

    // https://developer.android.com/guide/topics/ui/layout/recyclerview
    class StockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val companyImageView: ImageView = itemView.findViewById(R.id.companyImageView)
        val changesPercentageTextView: TextView =
            itemView.findViewById(R.id.changesPercentageTextView)
        val symbolTextView: TextView = itemView.findViewById(R.id.symbolTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.stock_item_row, parent, false)
        return StockViewHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val rowData = stocks[position]
        holder.apply {
            symbolTextView.text = rowData.stockEntity.symbol
            nameTextView.text = rowData.stockEntity.name
            priceTextView.text = rowData.stockEntity.price.toString()
            companyImageView.load(rowData.stockProfileEntity.image)
            changesPercentageTextView.text = rowData.stockProfileEntity.changesPercentage
            changesPercentageTextView.setTextColor(
                if (rowData.stockProfileEntity.isPositive) Color.GREEN else Color.RED
            )
        }
    }

    override fun getItemCount(): Int = stocks.size

    fun submitStocks(stockList: List<StockProfileData>) {
        stocks = stockList
        notifyDataSetChanged()
    }
}