package com.example.doa_app.domain.repository

import com.example.doa_app.data.model.mobile.PublicationMob
import com.example.doa_app.data.model.api.PublicationAPI
import retrofit2.Response

interface PublicationRepository {
    //Methods for CRUD of Publication
    suspend fun getAllPublications(): Response<List<PublicationAPI>>
    suspend fun getAllPublicationsOfInstitution(id: String): Response<List<PublicationMob>>
    suspend fun createPublication(publication: PublicationAPI): Response<PublicationMob>
}