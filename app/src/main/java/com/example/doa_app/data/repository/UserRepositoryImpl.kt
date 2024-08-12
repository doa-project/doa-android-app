package com.example.doa_app.data.repository

import com.example.doa_app.data.datasource.Service
import com.example.doa_app.data.model.api.User
import com.example.doa_app.domain.repository.UserRepository
import retrofit2.Response

class UserRepositoryImpl(private val service: Service): UserRepository {
    override suspend fun createUser(user: User): Response<User> {
        return service.createUser(user)
    }

    override suspend fun updateUser(id: String, user: User): Response<User> {
        return service.updateUser(id, user)
    }

    override suspend fun deleteUser(id: String): Response<User> {
        return service.deleteUser(id)
    }
}