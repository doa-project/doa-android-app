package com.example.doa_app.data.datasource

import com.example.doa_app.data.model.Campaign
import com.example.doa_app.data.model.Institution
import com.example.doa_app.data.model.Publication
import com.example.doa_app.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface Service{
    //USER CRUD
    @POST("user/create/")
    suspend fun createUser(@Body user: User): Response<User>
    @PUT("user/edit/{id}")
    suspend fun updateUser(@Path("id") id: String, @Body user: User): Response<User>
    @DELETE("user/delete/{id}")
    suspend fun deleteUser(@Path("id") id: String): Response<User>

    //INSTITUTION CRUD
    @POST("institution/create/")
    suspend fun createInstitution( @Body institution: Institution): Response<Institution>
    @PUT("institution/edit/{id}")
    suspend fun updateInstitution(@Path("id") id: String, @Body institution: Institution): Response<Institution>
    @DELETE("institution/delete/{id}")
    suspend fun deleteInstitution(@Path("id") id: String): Response<Institution>

    //CAMPAIN CRUD
    @POST("campain/create/")
    suspend fun createCampaign(@Body campaign: Campaign): Response<Campaign>
    @GET("campain/get/")
    suspend fun getAllCampaign(): Response<List<Campaign>>
    @GET("campain/get/{id_institution}")
    suspend fun getAllCampaignOfInstitution(@Path("id_institution") id: String): Response<List<Campaign>>

    //PUBLICATION CRUD
    @POST("publication/create/")
    suspend fun createPublication(@Body publication: Publication): Response<Publication>
    @GET("publication/get/")
    suspend fun getAllPublications(): Response<List<Publication>>
    @GET("publication/get/{id_institution}")
    suspend fun getAllPublicationsOfInstitution(@Path("id_institution") id: String): Response<List<Publication>>

}