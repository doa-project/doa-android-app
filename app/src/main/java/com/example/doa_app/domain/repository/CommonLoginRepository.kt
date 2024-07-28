package com.example.doa_app.domain.repository

import com.example.doa_app.data.model.Login
import com.example.doa_app.data.model.User
import retrofit2.Response

interface CommonLoginRepository {
    suspend fun login(login: Login): Response<User>
}