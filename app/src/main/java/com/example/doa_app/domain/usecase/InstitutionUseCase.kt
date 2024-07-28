package com.example.doa_app.domain.usecase

import com.example.doa_app.data.model.Institution
import com.example.doa_app.domain.repository.InstitutionRepository
import retrofit2.Response

class InstitutionUseCase(
    private val repository: InstitutionRepository
) {
    suspend fun createInstitution(institution: Institution): Response<Institution> = repository.createInstitution(institution)
    suspend fun updateInstitution(id: String, institution: Institution): Response<Institution> = repository.updateInstitution(id, institution)
    suspend fun deleteInstitution(id: String): Response<Institution> = repository.deleteInstitution(id)
}