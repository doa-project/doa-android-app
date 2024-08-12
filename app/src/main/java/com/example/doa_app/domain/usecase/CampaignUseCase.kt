package com.example.doa_app.domain.usecase

import com.example.doa_app.data.model.mobile.CampaignMob
import com.example.doa_app.data.model.api.CampaignAPI
import com.example.doa_app.domain.repository.CampaignRepository
import retrofit2.Response

class CampaignUseCase(
    private val repository: CampaignRepository
) {
    suspend fun getAllCampaign(): Response<List<CampaignAPI>> = repository.getAllCampaign()
    suspend fun getAllCampaignOfInstitution(id: String): Response<List<CampaignMob>> = repository.getAllCampaignOfInstitution(id)
    suspend fun createCampaign(campaign: CampaignAPI): Response<CampaignMob> = repository.createCampaign(campaign)
}