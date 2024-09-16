package com.example.doa_app.domain.repository;

import android.content.Context
import android.graphics.Bitmap
import com.example.doa_app.data.model.api.CampaignAPI
import com.example.doa_app.data.model.api.Institution
import com.example.doa_app.data.model.api.Login
import com.example.doa_app.data.model.api.User
import com.example.doa_app.data.model.mobile.CampaignMob
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
//    suspend fun updateInstitution(id: String, institution: Institution): Response<Institution>
//    suspend fun deleteInstitution(id: String): Response<Institution>
//    suspend fun getInstitution(id: String): Response<Institution>
    //
    suspend fun createUser(user: User): Response<User>
//    suspend fun updateUser(id: String, user: User): Response<User>
//    suspend fun deleteUser(id: String): Response<User>

    suspend fun uploadImage(image: Bitmap): String?
    suspend fun loadImage(url: String): Bitmap?
}
