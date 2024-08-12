package com.example.doa_app.domain.repository

import com.example.doa_app.data.model.mobile.CampaignMob
import com.example.doa_app.data.model.api.CampaignAPI
import retrofit2.Response

interface CampaignRepository {
    suspend fun getAllCampaign(): Response<List<CampaignAPI>>
    suspend fun getAllCampaignOfInstitution(id: String): Response<List<CampaignMob>>
    suspend fun createCampaign(campaign: CampaignAPI): Response<CampaignMob>
}