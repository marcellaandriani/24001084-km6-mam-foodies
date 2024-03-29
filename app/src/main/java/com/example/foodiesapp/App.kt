package com.example.foodiesapp

import android.app.Application
import com.example.foodiesapp.data.source.local.database.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}