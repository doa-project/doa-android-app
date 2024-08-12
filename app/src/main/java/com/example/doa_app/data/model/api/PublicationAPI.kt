package com.example.doa_app.data.model.api

data class PublicationAPI(
    val id: String?,
    val publicationId: Int?,
    val institutionId: String,
    val institutionName: String?,
    val institutionPhoto: String?,
    val description: String,
    val images: List<String>
)
