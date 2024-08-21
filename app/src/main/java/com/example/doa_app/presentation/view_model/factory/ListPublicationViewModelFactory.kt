package com.example.doa_app.presentation.view_model.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.doa_app.domain.usecase.CampaignUseCase
import com.example.doa_app.domain.usecase.PublicationUseCase
import com.example.doa_app.presentation.view_model.ListCampaignViewModel
import com.example.doa_app.presentation.view_model.ListPublicationViewModel
import com.example.doa_app.utils.SharedPreferences
import com.example.doa_app.utils.TreatmentApiObjects

class ListPublicationViewModelFactory(
    private val publicationUseCase: PublicationUseCase,
    private val treatmentApiObjects: TreatmentApiObjects,
    private val sharedPreferences: SharedPreferences
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListPublicationViewModel::class.java)) {
            return ListPublicationViewModel(publicationUseCase, treatmentApiObjects, sharedPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}