package com.example.doa_app.domain.usecase

import com.example.doa_app.data.model.Login
import com.example.doa_app.data.model.User
import com.example.doa_app.domain.repository.UserRepository
import retrofit2.Response

class UserUseCase(
    private val repository: UserRepository
) {
    suspend fun createUser(user: User): Response<User> = repository.createUser(user)
    suspend fun updateUser(id: String, user: User): Response<User> = repository.updateUser(id, user)
    suspend fun deleteUser(id: String): Response<User> = repository.deleteUser(id)
}