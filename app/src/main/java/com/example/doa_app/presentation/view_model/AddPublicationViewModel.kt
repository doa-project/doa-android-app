package com.example.doa_app.presentation.view_model

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doa_app.data.model.api.CampaignAPI
import com.example.doa_app.data.model.api.Institution
import com.example.doa_app.data.model.mobile.Image
import com.example.doa_app.domain.usecase.UseCases
import com.example.doa_app.utils.TreatmentApiObjects
import kotlinx.coroutines.launch

class AddPublicationViewModel(
    private val useCases: UseCases,
    private val treatmentApiObjects: TreatmentApiObjects
) : ViewModel() {

    private val _loadingVisibility = MutableLiveData<Int>()
    val loadingVisibility: LiveData<Int> get() = _loadingVisibility

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _selectedImages = MutableLiveData<List<Image>>()
    val selectedImages: LiveData<List<Image>> get() = _selectedImages

    private var institution: Institution? = null
    init {
        _selectedImages.value = emptyList()
        _loadingVisibility.value = View.GONE
    }

    fun setInstitution(institution: Institution) {
        this.institution = institution
    }

    fun addImage(image: Image) {
        _selectedImages.value = _selectedImages.value?.plus(image)
    }
    fun clearImages() {
        _selectedImages.value = emptyList()
    }

    fun createPublicationOrCampaign(description: String, address: String, date: String) {
        if (description.isBlank()) {
            _errorMessage.value = "Description cannot be empty"
            return
        }

        institution?.let {
            _loadingVisibility.value = View.VISIBLE

            viewModelScope.launch {
                try {
                    createCampaign(description, address, date)
                    _loadingVisibility.value = View.INVISIBLE
                } catch (e: Exception) {
                    _loadingVisibility.value = View.INVISIBLE
                    _errorMessage.value = "Failed to create: ${e.message}"
                    Log.d("AddPublicationViewModel", "Failed to create: ${e.message}")
                    Log.d("AddPublicationViewModel", "Failed to create: ${e.stackTrace}")
                    Log.d("AddPublicationViewModel", "Failed to create: ${e.cause}")
                }
            }
        } ?: run {
            _errorMessage.value = "Institution is not set"
        }
    }

    private suspend fun createCampaign(description: String, local: String, date: String) {
        val imageToString = selectedImages.value?.let { treatmentApiObjects.imageListToStringList(it) }!!.toList()
        val campaignAPI = CampaignAPI(
            null,
            null,
            institution?.institutionId!!.toString(),
            null,
            null,
            description,
            imageToString,
            local,
            date
        )
        Log.d("AddPublicationViewModel", "createCampaign: $campaignAPI")
        useCases.createCampaign(campaignAPI).let {
            if (it.isSuccessful) {
                _loadingVisibility.value = View.INVISIBLE
            }
            else {
                _loadingVisibility.value = View.INVISIBLE
                _errorMessage.value = "Failed to create"
                Log.d("AddPublicationViewModel", "Failed to create: ${it.errorBody()}")
                Log.d("AddPublicationViewModel", "Failed to create: ${it.code()}")
                Log.d("AddPublicationViewModel", "Failed to create: ${it.message()}")
            }
        }
    }
}
