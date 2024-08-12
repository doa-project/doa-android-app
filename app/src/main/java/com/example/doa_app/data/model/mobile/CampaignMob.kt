package com.example.doa_app.data.model.mobile

data class CampaignMob(
    val id: String?,
    val campaignId: Int?,
    val institutionId: String,
    val institutionName: String?,
    val institutionPhoto: String?,
    val description: String,
    val images: MutableList<Image>,
    val endDate: String,
    val local: String
)
