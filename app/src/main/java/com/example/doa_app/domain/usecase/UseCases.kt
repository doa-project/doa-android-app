package com.example.doa_app.domain.usecase

import android.content.Context
import android.graphics.Bitmap
import com.example.doa_app.data.model.api.CampaignAPI
import com.example.doa_app.data.model.api.Institution
import com.example.doa_app.data.model.api.Login
import com.example.doa_app.data.model.api.User
import com.example.doa_app.data.model.mobile.CampaignMob
import com.example.doa_app.domain.repository.Repository
import retrofit2.Response

class UseCases(
    private val repository: Repository
) {
    //Methods for CRUD of Campaign
    suspend fun getAllCampaign(): Response<List<CampaignAPI>> = repository.getAllCampaign()
    suspend fun getAllCampaignOfInstitution(id: String): Response<List<CampaignMob>> = repository.getAllCampaignOfInstitution(id)
    suspend fun createCampaign(campaign: CampaignAPI): Response<CampaignMob> = repository.createCampaign(campaign)
    //
    suspend fun login(login: Login): Response<Any> = repository.login(login)
    //
    suspend fun createInstitution(institution: Institution): Response<Institution> = repository.createInstitution(institution)
//    suspend fun updateInstitution(id: String, institution: Institution): Response<Institution> = repository.updateInstitution(id, institution)
//    suspend fun deleteInstitution(id: String): Response<Institution> = repository.deleteInstitution(id)
//    suspend fun getInstitution(id: String): Response<Institution> = repository.getInstitution(id)
//    //
    suspend fun createUser(user: User): Response<User> = repository.createUser(user)
//    suspend fun updateUser(id: String, user: User): Response<User> = repository.updateUser(id, user)
//    suspend fun deleteUser(id: String): Response<User> = repository.deleteUser(id)
    suspend fun uploadImage(image: Bitmap): String? = repository.uploadImage(image)

    suspend fun loadImage(url: String): Bitmap? = repository.loadImage(url)
}