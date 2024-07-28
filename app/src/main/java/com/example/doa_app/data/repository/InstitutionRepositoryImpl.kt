package com.example.doa_app.data.repository

import com.example.doa_app.data.datasource.Service
import com.example.doa_app.data.model.Institution
import com.example.doa_app.domain.repository.InstitutionRepository
import retrofit2.Response

class InstitutionRepositoryImpl(private val service: Service): InstitutionRepository {
    override suspend fun createInstitution(institution: Institution): Response<Institution> {
        return service.createInstitution(institution)
    }

    override suspend fun updateInstitution(
        id: String,
        institution: Institution
    ): Response<Institution> {
        return service.updateInstitution(id, institution)
    }

    override suspend fun deleteInstitution(id: String): Response<Institution> {
        return service.deleteInstitution(id)
    }

    override suspend fun getInstitution(id: String): Response<Institution> {
        return service.getInstitution(id)
    }
}