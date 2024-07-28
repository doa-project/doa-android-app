package com.example.doa_app

import com.example.doa_app.data.datasource.Service
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    // Provide Retrofit instance
    single<Service> {
        Retrofit.Builder()
            .baseUrl("https://api.url.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Service::class.java)
    }
}
