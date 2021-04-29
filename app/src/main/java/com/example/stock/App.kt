package com.example.stock

import android.app.Application

// https://developer.android.com/training/dependency-injection/dagger-android
class App : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.create()
        appComponent.inject(this)
    }
}