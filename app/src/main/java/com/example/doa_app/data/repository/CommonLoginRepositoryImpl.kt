package com.example.doa_app.data.repository

import com.example.doa_app.data.datasource.Service
import com.example.doa_app.data.model.Login
import com.example.doa_app.data.model.User
import com.example.doa_app.domain.repository.CommonLoginRepository
import retrofit2.Response

class CommonLoginRepositoryImpl(private val service: Service): CommonLoginRepository {
    override suspend fun login(login: Login): Response<User> {
        return service.login(login)
    }
}