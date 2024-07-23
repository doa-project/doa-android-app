package com.example.doa_app.domain.repository

import com.example.doa_app.data.model.Campaign
import com.example.doa_app.data.model.Institution
import com.example.doa_app.data.model.Login
import com.example.doa_app.data.model.Publication
import com.example.doa_app.data.model.User
import retrofit2.Response
//Essa classe representa a camada de comunicação da coleta de dados com a lógica de negócio

interface Repository {

    //Methods for CRUD of Campaign
    suspend fun getAllCampaign(): Response<List<Campaign>>
    suspend fun getAllCampaignOfInstitution(id: String): Response<List<Campaign>>
    suspend fun createCampaign(campaign: Campaign): Response<Campaign>

    //Methods for CRUD of Publication
    suspend fun getAllPublications(): Response<List<Publication>>
    suspend fun getAllPublicationsOfInstitution(id: String): Response<List<Publication>>
    suspend fun createPublication(publication: Publication): Response<Publication>

    //Methods for CRUD of User
    suspend fun createUser(user: User): Response<User>
    suspend fun updateUser(id: String, user: User): Response<User>
    suspend fun deleteUser(id: String): Response<User>
    suspend fun login(login: Login): Response<User>

    //Methods for CRUD of Institution
    suspend fun createInstitution(institution: Institution): Response<Institution>
    suspend fun updateInstitution(id: String, institution: Institution): Response<Institution>
    suspend fun deleteInstitution(id: String): Response<Institution>
}