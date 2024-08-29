package com.example.doa_app.presentation.view_model.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.doa_app.domain.usecase.UseCases
import com.example.doa_app.presentation.view_model.AddPublicationViewModel
import com.example.doa_app.utils.TreatmentApiObjects

class AddPublicationViewModelFactory(
    private val useCases: UseCases,
    private val treatmentApiObjects: TreatmentApiObjects
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddPublicationViewModel::class.java)) {
            return AddPublicationViewModel(useCases, treatmentApiObjects) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}