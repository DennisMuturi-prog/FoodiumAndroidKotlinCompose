package com.example.foodium

import android.app.Application

class MyApplication : Application() {
    // Instance of AppContainer that will be used by all the Activities of the app

    companion object {
        lateinit var appContainer: AppContainer
    }
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }

}