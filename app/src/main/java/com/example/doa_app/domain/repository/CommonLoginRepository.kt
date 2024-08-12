package com.example.doa_app.domain.repository

import com.example.doa_app.data.model.api.Login
import retrofit2.Response

interface CommonLoginRepository {
    suspend fun login(login: Login): Response<Any>
}