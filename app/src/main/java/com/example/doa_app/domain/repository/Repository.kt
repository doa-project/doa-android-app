package com.example.doa_app.domain.repository

import com.example.doa_app.data.model.Campaign
import com.example.doa_app.data.model.Institution
import com.example.doa_app.data.model.Publication
import com.example.doa_app.data.model.User
import retrofit2.Response

interface Repository {

    //CAMPAIGN CRUD
    suspend fun getAllCampaign(): Response<List<Campaign>>
    suspend fun getAllCampaignOfInstitution(id: String): Response<List<Campaign>>
    suspend fun createCampaign(campaign: Campaign): Response<Campaign>

    //PUBLICATION CRUD
    suspend fun getAllPublications(): Response<List<Publication>>
    suspend fun getAllPublicationsOfInstitution(id: String): Response<List<Publication>>
    suspend fun createPublication(publication: Publication): Response<Publication>

    //USER CRUD
    suspend fun createUser(user: User): Response<User>
    suspend fun updateUser(id: String, user: User): Response<User>
    suspend fun deleteUser(id: String): Response<User>

    //INSTITUTION CRUD
    suspend fun createInstitution(institution: Institution): Response<Institution>
    suspend fun updateInstitution(id: String, institution: Institution): Response<Institution>
    suspend fun deleteInstitution(id: String): Response<Institution>
}