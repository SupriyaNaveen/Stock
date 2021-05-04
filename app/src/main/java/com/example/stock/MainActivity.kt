package com.example.stock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.stock.network.StockApiQuery
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var stockApiQuery: StockApiQuery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO: add back arrow handling
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Use navigation graph for bottom navigation bar
        // Reference taken https://github.com/BrianaNzivu/BottomNavigationBar
        val navView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.stockFragment,
            R.id.settingsFragment
        ).build()
        val navController = Navigation.findNavController(this, R.id.navHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navView, navController)

        // Fetch the data and save it to storage.
        // TODO: Move this to Work manager
        stockApiQuery()

        // TODO: Add theme based on preference value
    }
}