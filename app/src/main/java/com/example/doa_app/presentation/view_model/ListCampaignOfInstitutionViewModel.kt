package com.example.doa_app.presentation.view_model

import androidx.lifecycle.ViewModel
import com.example.doa_app.domain.usecase.UseCases
import com.example.doa_app.utils.SharedPreferences
import com.example.doa_app.utils.TreatmentApiObjects

class ListCampaignOfInstitutionViewModel(
    val useCases: UseCases,
    val treatmentApiObjects: TreatmentApiObjects,
    val sharedPreferences: SharedPreferences
    ): ViewModel() {

}