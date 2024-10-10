package com.example.doa_app.data.model.api

data class Institution(
    val id: String?,
    val institutionId: Int,
    val name: String,
    val email: String,
    val description: String,
    val local: String,
    val phone: String,
    val photo: String
)
