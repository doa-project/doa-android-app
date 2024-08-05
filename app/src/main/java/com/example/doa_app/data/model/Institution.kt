package com.example.doa_app.data.model

import android.util.Base64

data class Institution(
    val id: Int,
    val name: String,
    val email: String,
    val description: String,
    val local: String,
    val phone: String,
    val photo: Base64
)
