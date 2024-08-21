package com.example.doa_app.presentation.view_model.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.doa_app.domain.usecase.CampaignUseCase
import com.example.doa_app.presentation.view_model.ListCampaignViewModel
import com.example.doa_app.utils.TreatmentApiObjects

class ListCampaignViewModelFactory(
    private val campaignUseCase: CampaignUseCase,
    private val treatmentApiObjects: TreatmentApiObjects
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListCampaignViewModel::class.java)) {
            return ListCampaignViewModel(campaignUseCase, treatmentApiObjects) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
