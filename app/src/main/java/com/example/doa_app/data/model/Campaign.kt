package com.example.doa_app.data.model

import android.util.Base64

data class Campaign(
    val id: String?,
    val institutionId: String,
    val institutionName: String?,
    val institutionPhoto: Base64?,
    val description: String,
    val images: List<Image>,
    val endDate: String,
    val local: String
)
