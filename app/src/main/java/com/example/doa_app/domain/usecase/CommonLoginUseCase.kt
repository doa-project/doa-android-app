package com.example.doa_app.domain.usecase

import com.example.doa_app.data.model.Login
import com.example.doa_app.data.model.User
import com.example.doa_app.domain.repository.CommonLoginRepository
import retrofit2.Response

class CommonLoginUseCase(
    private val repository: CommonLoginRepository
) {
    suspend fun login(login: Login): Response<User> = repository.login(login)
}