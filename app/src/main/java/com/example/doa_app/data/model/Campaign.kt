package com.example.doa_app.data.model

import android.graphics.Bitmap
import android.util.Base64

data class Campaign(
    val id: String?,
    val institutionId: String,
    val institutionName: String?,
    val institutionPhoto: Bitmap?,
    val description: String,
    val images: List<Image>,
    val endDate: String,
    val local: String
)
