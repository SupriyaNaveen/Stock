package com.example.stock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.stock.network.StockApiQuery
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {
    @Inject
    lateinit var stockApiQuery: StockApiQuery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)
        stockApiQuery()
    }

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = injector
}