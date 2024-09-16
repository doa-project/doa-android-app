package com.example.doa_app.presentation.view_model

import androidx.lifecycle.ViewModel
import com.example.doa_app.domain.usecase.UseCases
import com.example.doa_app.utils.SharedPreferences

class ListCampaignOfInstitutionViewModel(
    val useCases: UseCases,
    val sharedPreferences: SharedPreferences
    ): ViewModel() {

}