package com.example.doa_app.domain.usecase

import com.example.doa_app.data.model.mobile.PublicationMob
import com.example.doa_app.data.model.api.PublicationAPI
import com.example.doa_app.domain.repository.PublicationRepository
import retrofit2.Response

class PublicationUseCase(
    private val repository: PublicationRepository
) {
    suspend fun getAllPublications(): Response<List<PublicationAPI>> = repository.getAllPublications()

    suspend fun getAllPublicationsOfInstitution(id: String): Response<List<PublicationMob>> = repository.getAllPublicationsOfInstitution(id)

    suspend fun createPublication(publication: PublicationAPI): Response<PublicationMob> = repository.createPublication(publication)
}