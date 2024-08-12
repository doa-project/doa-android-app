package com.example.doa_app

import com.example.doa_app.data.datasource.Service
import com.example.doa_app.utils.gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val service: Service by lazy {
        Retrofit.Builder()
            .baseUrl("https://doa-api.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(Service::class.java)
    }
}