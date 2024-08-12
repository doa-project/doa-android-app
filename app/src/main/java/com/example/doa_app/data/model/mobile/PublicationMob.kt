package com.example.doa_app.data.model.mobile

data class PublicationMob(
    val id: String?,
    val publicationId: Int?,
    val institutionId: String,
    val institutionName: String?,
    val institutionPhoto: String?,
    val description: String,
    val images: List<Image>
)
