package com.example.doa_app

import com.example.doa_app.data.datasource.Service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val service: Service by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.url.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Service::class.java)
    }
}