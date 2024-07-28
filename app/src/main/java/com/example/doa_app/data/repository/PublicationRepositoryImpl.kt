package com.example.doa_app.data.repository;

import com.example.doa_app.data.datasource.Service
import com.example.doa_app.data.model.Publication
import com.example.doa_app.domain.repository.PublicationRepository
import retrofit2.Response

class PublicationRepositoryImpl(private val service: Service) : PublicationRepository {
    override suspend fun getAllPublications(): Response<List<Publication>> {
        return service.getAllPublications()
    }

    override suspend fun getAllPublicationsOfInstitution(id: String): Response<List<Publication>> {
        return service.getAllPublicationsOfInstitution(id)
    }

    override suspend fun createPublication(publication: Publication): Response<Publication> {
        return service.createPublication(publication)
    }
}
