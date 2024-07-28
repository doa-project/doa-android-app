package com.example.doa_app.domain.repository

import com.example.doa_app.data.model.Publication
import retrofit2.Response

interface PublicationRepository {
    //Methods for CRUD of Publication
    suspend fun getAllPublications(): Response<List<Publication>>
    suspend fun getAllPublicationsOfInstitution(id: String): Response<List<Publication>>
    suspend fun createPublication(publication: Publication): Response<Publication>
}