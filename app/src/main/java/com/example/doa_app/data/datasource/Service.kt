package com.example.doa_app.data.datasource

import com.example.doa_app.data.model.mobile.CampaignMob
import com.example.doa_app.data.model.api.CampaignAPI
import com.example.doa_app.data.model.api.Institution
import com.example.doa_app.data.model.api.Login
import com.example.doa_app.data.model.mobile.PublicationMob
import com.example.doa_app.data.model.api.PublicationAPI
import com.example.doa_app.data.model.api.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

//Classe que representa a camada de comunicação com a API
interface Service{
    //USER CRUD
    @POST("user/create")
    suspend fun createUser(@Body user: User): Response<User>
    @PUT("user/edit/{id}")
    suspend fun updateUser(@Path("id") id: String, @Body user: User): Response<User>
    @DELETE("user/delete/{id}")
    suspend fun deleteUser(@Path("id") id: String): Response<User>

    //LOGIN
    @POST("user/login")
    suspend fun login(@Body login: Login): Response<Any>

    //INSTITUTION CRUD
    @POST("institution/create")
    suspend fun createInstitution( @Body institution: Institution): Response<Institution>
    @PUT("institution/edit/{id}")
    suspend fun updateInstitution(@Path("id") id: String, @Body institution: Institution): Response<Institution>
    @DELETE("institution/delete/{id}")
    suspend fun deleteInstitution(@Path("id") id: String): Response<Institution>
    @GET("institution/get/{id}")
    suspend fun getInstitution(@Path("id") id: String): Response<Institution>

    //CAMPAIN CRUD
    @POST("campaign/create")
    suspend fun createCampaign(@Body campaign: CampaignAPI): Response<CampaignMob>
    @GET("campaign/get")
    suspend fun getAllCampaign(): Response<List<CampaignAPI>>
    @GET("campaign/get/{id_institution}")
    suspend fun getAllCampaignOfInstitution(@Path("id_institution") id: String): Response<List<CampaignMob>>

    //PUBLICATION CRUD
    @POST("publication/create")
    suspend fun createPublication(@Body publication: PublicationAPI): Response<PublicationMob>
    @GET("publication/get")
    suspend fun getAllPublications(): Response<List<PublicationAPI>>
    @GET("publication/get/{id_institution}")
    suspend fun getAllPublicationsOfInstitution(@Path("id_institution") id: String): Response<List<PublicationMob>>

}