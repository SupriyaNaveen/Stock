package com.example.stock

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

// https://developer.android.com/training/dependency-injection/dagger-android
class App : Application(), HasAndroidInjector {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.inject(this)
    }

    @Inject lateinit var injector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = injector
}