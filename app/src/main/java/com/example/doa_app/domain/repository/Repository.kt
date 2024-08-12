package com.example.doa_app.domain.repository;

import com.example.doa_app.data.model.api.CampaignAPI
import com.example.doa_app.data.model.api.Institution
import com.example.doa_app.data.model.api.Login
import com.example.doa_app.data.model.api.PublicationAPI
import com.example.doa_app.data.model.api.User
import com.example.doa_app.data.model.mobile.CampaignMob
import com.example.doa_app.data.model.mobile.PublicationMob
import retrofit2.Response
interface Repository {
    //
    suspend fun getAllCampaign(): Response<List<CampaignAPI>>
    suspend fun getAllCampaignOfInstitution(id: String): Response<List<CampaignMob>>
    suspend fun createCampaign(campaign: CampaignAPI): Response<CampaignMob>
    //
    suspend fun login(login: Login): Response<Any>
    //
    suspend fun createInstitution(institution: Institution): Response<Institution>
    suspend fun updateInstitution(id: String, institution: Institution): Response<Institution>
    suspend fun deleteInstitution(id: String): Response<Institution>
    suspend fun getInstitution(id: String): Response<Institution>
    //
    suspend fun getAllPublications(): Response<List<PublicationAPI>>
    suspend fun getAllPublicationsOfInstitution(id: String): Response<List<PublicationMob>>
    suspend fun createPublication(publication: PublicationAPI): Response<PublicationMob>
    //
    suspend fun createUser(user: User): Response<User>
    suspend fun updateUser(id: String, user: User): Response<User>
    suspend fun deleteUser(id: String): Response<User>
}
