package com.example.doa_app.domain.repository

import com.example.doa_app.data.model.Institution
import retrofit2.Response

interface InstitutionRepository {
    //Methods for CRUD of Institution
    suspend fun createInstitution(institution: Institution): Response<Institution>
    suspend fun updateInstitution(id: String, institution: Institution): Response<Institution>
    suspend fun deleteInstitution(id: String): Response<Institution>
    suspend fun getInstitution(id: String): Response<Institution>
}