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
import com.example.doa_app.utils.SharedPreferences
import com.example.doa_app.utils.gson
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class AddPublicationViewModel(
    private val useCases: UseCases,
    private val sharedPreferences: SharedPreferences
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

    fun getInstitution() {
        sharedPreferences.getString("loggedInstitution")?.let {
            this.institution = gson.fromJson(it, Institution::class.java)
        }
    }

    fun addImage(image: Image) {
        _selectedImages.value = _selectedImages.value?.plus(image)
    }
    fun clearImages() {
        _selectedImages.value = emptyList()
    }

    fun createPublicationOrCampaign(description: String, address: String, date: String) {
        if (description.isBlank() || address.isBlank() || date.isBlank()) {
            _errorMessage.value = "Preencha os campos"
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
                    _errorMessage.value = "Erro ao criar campanha"
                    Log.d("AddPublicationViewModel", "Failed to create: ${e.message}")
                    Log.d("AddPublicationViewModel", "Failed to create: ${e.stackTrace}")
                    Log.d("AddPublicationViewModel", "Failed to create: ${e.cause}")
                }
            }
        } ?: run {
            _errorMessage.value = "Login não efetuado"
        }
    }

    private suspend fun createCampaign(description: String, local: String, date: String) {
        val listOfUrls = uploadImages()
        val campaignAPI = CampaignAPI(
            null,
            null,
            institution?.institutionId!!.toString(),
            null,
            null,
            description,
            listOfUrls,
            date,
            local

        )
        Log.d("AddPublicationViewModel", "createCampaign: $campaignAPI")
        useCases.createCampaign(campaignAPI).let {
            if (it.isSuccessful) {
                _loadingVisibility.value = View.INVISIBLE
            }
            else {
                _loadingVisibility.value = View.INVISIBLE
                _errorMessage.value = "Erro ao criar campanha"
                Log.d("AddPublicationViewModel", "Failed to create: ${it.errorBody()}")
                Log.d("AddPublicationViewModel", "Failed to create: ${it.code()}")
                Log.d("AddPublicationViewModel", "Failed to create: ${it.body()}")
                Log.d("AddPublicationViewModel", "Failed to create: ${it.message()}")
            }
        }
    }

    private suspend fun uploadImages(): MutableList<String> {
        val listOfUrlImages = mutableListOf<String>()

        val images = _selectedImages.value ?: return listOfUrlImages

        coroutineScope {
            images.map { image ->
                async {
                    try {
                        val url = useCases.uploadImage(image.image)
                        if (url != null) {
                            listOfUrlImages.add(url)
                        } else {
                            logError("Failed to upload image")
                        }
                    } catch (e: Exception) {
                        logError("Failed to upload image: ${e.message}", e)
                    }
                }
            }.awaitAll()
        }
        return listOfUrlImages
    }
    private fun logError(message: String, exception: Exception? = null) {
        _errorMessage.value = message
        Log.d("AddPublicationViewModel", message)
        exception?.let {
            Log.d("AddPublicationViewModel", "Exception: ${it.stackTrace}")
            Log.d("AddPublicationViewModel", ": ${it.message}")
            Log.d("AddPublicationViewModel", "Cause: ${it.cause}")
        }
    }

}
