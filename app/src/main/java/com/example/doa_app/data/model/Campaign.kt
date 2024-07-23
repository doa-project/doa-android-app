package com.example.doa_app.data.model

import android.util.Base64

data class Campaign(
    val id: Int,
    val institutionName: String,
    val institutionPhoto: Base64,
    val description: String,
    val images: List<Base64>,
    val endDate: String,
    val local: String
)
