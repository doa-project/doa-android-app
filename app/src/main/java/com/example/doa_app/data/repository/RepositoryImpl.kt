package com.example.doa_app.data.repository

import com.example.doa_app.data.datasource.Service
import com.example.doa_app.data.model.api.CampaignAPI
import com.example.doa_app.data.model.api.Institution
import com.example.doa_app.data.model.api.Login
import com.example.doa_app.data.model.api.User
import com.example.doa_app.data.model.mobile.CampaignMob
import com.example.doa_app.domain.repository.Repository
import retrofit2.Response

class RepositoryImpl(val service: Service): Repository {
    override suspend fun getAllCampaign(): Response<List<CampaignAPI>> {
        return service.getAllCampaign()
    }

    override suspend fun getAllCampaignOfInstitution(id: String): Response<List<CampaignMob>> {
        return service.getAllCampaignOfInstitution(id)
    }

    override suspend fun createCampaign(campaign: CampaignAPI): Response<CampaignMob> {
        return service.createCampaign(campaign)
    }
    override suspend fun login(login: Login): Response<Any> {
        return service.login(login)
    }
    override suspend fun createInstitution(institution: Institution): Response<Institution> {
        return service.createInstitution(institution)
    }

    override suspend fun updateInstitution(
        id: String,
        institution: Institution
    ): Response<Institution> {
        return service.updateInstitution(id, institution)
    }

    override suspend fun deleteInstitution(id: String): Response<Institution> {
        return service.deleteInstitution(id)
    }

    override suspend fun getInstitution(id: String): Response<Institution> {
        return service.getInstitution(id)
    }
    override suspend fun createUser(user: User): Response<User> {
        return service.createUser(user)
    }

    override suspend fun updateUser(id: String, user: User): Response<User> {
        return service.updateUser(id, user)
    }

    override suspend fun deleteUser(id: String): Response<User> {
        return service.deleteUser(id)
    }
}