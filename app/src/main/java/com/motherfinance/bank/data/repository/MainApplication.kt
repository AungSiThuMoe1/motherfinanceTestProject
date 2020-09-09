package com.motherfinance.bank.data.repository

import android.app.Application
import com.orhanobut.hawk.Hawk

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Hawk.init(applicationContext).build()
    }
}