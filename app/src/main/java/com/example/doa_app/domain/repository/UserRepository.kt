package com.example.doa_app.domain.repository

import com.example.doa_app.data.model.User
import retrofit2.Response

interface UserRepository {
    //Methods for CRUD of User
    suspend fun createUser(user: User): Response<User>
    suspend fun updateUser(id: String, user: User): Response<User>
    suspend fun deleteUser(id: String): Response<User>
}