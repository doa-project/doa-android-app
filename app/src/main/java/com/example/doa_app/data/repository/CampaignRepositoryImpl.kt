package com.example.doa_app.data.repository

import com.example.doa_app.data.datasource.Service
import com.example.doa_app.data.model.mobile.CampaignMob
import com.example.doa_app.data.model.api.CampaignAPI
import com.example.doa_app.domain.repository.CampaignRepository
import retrofit2.Response

class CampaignRepositoryImpl(private val service: Service): CampaignRepository {
    override suspend fun getAllCampaign(): Response<List<CampaignAPI>> {
        return service.getAllCampaign()
    }

    override suspend fun getAllCampaignOfInstitution(id: String): Response<List<CampaignMob>> {
        return service.getAllCampaignOfInstitution(id)
    }

    override suspend fun createCampaign(campaign: CampaignAPI): Response<CampaignMob> {
        return service.createCampaign(campaign)
    }
}