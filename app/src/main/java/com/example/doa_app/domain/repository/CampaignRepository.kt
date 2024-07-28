package com.example.doa_app.domain.repository

import com.example.doa_app.data.model.Campaign
import retrofit2.Response

interface CampaignRepository {
    suspend fun getAllCampaign(): Response<List<Campaign>>
    suspend fun getAllCampaignOfInstitution(id: String): Response<List<Campaign>>
    suspend fun createCampaign(campaign: Campaign): Response<Campaign>
}