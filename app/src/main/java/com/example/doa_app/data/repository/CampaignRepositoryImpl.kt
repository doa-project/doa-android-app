package com.example.doa_app.data.repository

import com.example.doa_app.data.datasource.Service
import com.example.doa_app.data.model.Campaign
import com.example.doa_app.domain.repository.CampaignRepository
import retrofit2.Response

class CampaignRepositoryImpl(private val service: Service): CampaignRepository {
    override suspend fun getAllCampaign(): Response<List<Campaign>> {
        return service.getAllCampaign()
    }

    override suspend fun getAllCampaignOfInstitution(id: String): Response<List<Campaign>> {
        return service.getAllCampaignOfInstitution(id)
    }

    override suspend fun createCampaign(campaign: Campaign): Response<Campaign> {
        return service.createCampaign(campaign)
    }
}