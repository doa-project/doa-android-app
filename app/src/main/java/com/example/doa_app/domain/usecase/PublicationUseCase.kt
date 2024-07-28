package com.example.doa_app.domain.usecase

import com.example.doa_app.data.model.Publication
import com.example.doa_app.domain.repository.PublicationRepository
import retrofit2.Response

class PublicationUseCase(
    private val repository: PublicationRepository
) {
    suspend fun getAllPublications(): Response<List<Publication>> = repository.getAllPublications()

    suspend fun getAllPublicationsOfInstitution(id: String): Response<List<Publication>> = repository.getAllPublicationsOfInstitution(id)

    suspend fun createPublication(publication: Publication): Response<Publication> = repository.createPublication(publication)
}