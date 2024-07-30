package com.example.doa_app.data.model

import android.graphics.Bitmap
import android.util.Base64

data class Publication(
    val id: Int?,
    val institutionId: String,
    val institutionName: String?,
    val institutionPhoto: Bitmap?,
    val description: String,
    val images: List<Image>?,
)
