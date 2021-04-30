package com.example.stock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.stock.network.StockApiQuery
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var stockApiQuery: StockApiQuery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)
        stockApiQuery()
    }
}