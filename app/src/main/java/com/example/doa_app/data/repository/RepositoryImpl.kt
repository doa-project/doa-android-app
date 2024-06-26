package com.example.doa_app.data.repository

import com.example.doa_app.data.datasource.Service
import com.example.doa_app.data.model.Campaign
import com.example.doa_app.data.model.Institution
import com.example.doa_app.data.model.Publication
import com.example.doa_app.data.model.User
import com.example.doa_app.domain.repository.Repository
import retrofit2.Response
//Essa classe representa a camada de comunicação da coleta de dados com a lógica de negócio.
// Ela implementa a interface Repository, garantindo a segurança sobre as operações aplicadas nos dados


class RepositoryImpl(private val service: Service): Repository {
    override suspend fun getAllCampaign(): Response<List<Campaign>> {
        return service.getAllCampaign()
    }

    override suspend fun getAllCampaignOfInstitution(id: String): Response<List<Campaign>> {
        return service.getAllCampaignOfInstitution(id)
    }

    override suspend fun createCampaign(campaign: Campaign): Response<Campaign> {
        return service.createCampaign(campaign)
    }

    override suspend fun getAllPublications(): Response<List<Publication>> {
        return service.getAllPublications()
    }

    override suspend fun getAllPublicationsOfInstitution(id: String): Response<List<Publication>> {
        return service.getAllPublicationsOfInstitution(id)
    }

    override suspend fun createPublication(publication: Publication): Response<Publication> {
        return service.createPublication(publication)
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
}