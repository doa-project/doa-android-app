package com.example.doa_app

import android.app.Application
import com.example.doa_app.utils.SharedPreferences
import com.google.firebase.FirebaseApp
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@Application)
            modules(appModule)
        }

    }
    override fun onTerminate() {
        super.onTerminate()
        val sharedPref = SharedPreferences(this, "doa_app_cache")
        sharedPref.clearKey("currentCampaign")
        sharedPref.clearKey("currentPublication")
    }
}
