package com.example.doa_app.data.repository;

import com.example.doa_app.data.datasource.Service
import com.example.doa_app.data.model.mobile.PublicationMob
import com.example.doa_app.data.model.api.PublicationAPI
import com.example.doa_app.domain.repository.PublicationRepository
import retrofit2.Response

class PublicationRepositoryImpl(private val service: Service) : PublicationRepository {
    override suspend fun getAllPublications(): Response<List<PublicationAPI>> {
        return service.getAllPublications()
    }

    override suspend fun getAllPublicationsOfInstitution(id: String): Response<List<PublicationMob>> {
        return service.getAllPublicationsOfInstitution(id)
    }

    override suspend fun createPublication(publication: PublicationAPI): Response<PublicationMob> {
        return service.createPublication(publication)
    }
}
