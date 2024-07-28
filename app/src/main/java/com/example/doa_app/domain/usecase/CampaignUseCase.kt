package com.example.doa_app.domain.usecase

import com.example.doa_app.data.model.Campaign
import com.example.doa_app.domain.repository.CampaignRepository
import retrofit2.Response

class CampaignUseCase(
    private val repository: CampaignRepository
) {
    suspend fun getAllCampaign(): Response<List<Campaign>> = repository.getAllCampaign()
    suspend fun getAllCampaignOfInstitution(id: String): Response<List<Campaign>> = repository.getAllCampaignOfInstitution(id)
    suspend fun createCampaign(campaign: Campaign): Response<Campaign> = repository.createCampaign(campaign)
}