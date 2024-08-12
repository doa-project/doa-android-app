package com.example.doa_app.data.model.api

data class CampaignAPI (
    val id: String?,
    val campaignId: Int?,
    val institutionId: String,
    val institutionName: String?,
    val institutionPhoto: String?,
    val description: String,
    val images: List<String>,
    val endDate: String,
    val local: String
)
