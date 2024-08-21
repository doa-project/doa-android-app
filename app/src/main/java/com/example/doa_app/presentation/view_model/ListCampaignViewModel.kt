package com.example.doa_app.presentation.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doa_app.data.model.mobile.CampaignMob
import com.example.doa_app.domain.usecase.CampaignUseCase
import com.example.doa_app.utils.TreatmentApiObjects
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException

class ListCampaignViewModel(
    private val campaignUseCase: CampaignUseCase,
    private val treatmentApiObjects: TreatmentApiObjects
) : ViewModel() {

    val campaignMobList = MutableLiveData<List<CampaignMob>>()
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun getAllCampaigns() {
        loading.value = true
        viewModelScope.launch {
            try {
                val response = campaignUseCase.getAllCampaign().body()
                if (response.isNullOrEmpty()) {
                    errorMessage.value = "No campaigns found!"
                    Log.e("ListCampaignViewModel", "No publications found")
                } else {
                    Log.d("ListCampaignViewModel", "Response: $response")
                    campaignMobList.value = treatmentApiObjects.campaignApiToCampaignList(response)
                }
            } catch (e: IOException) {
                Log.e("ListCampaignViewModel", "IOException, you might not have internet connection: ${e.message}")
                errorMessage.value = "You might not have internet connection"
            } catch (e: TimeoutException) {
                Log.e("ListCampaignViewModel", "TimeoutException, request timed out: ${e.message}")
                errorMessage.value = "Request timed out"
            } catch (e: Exception) {
                Log.e("ListCampaignViewModel", "Exception, an error occurred: ${e.message}")
                errorMessage.value = "An error occurred"
            } finally {
                loading.value = false
            }
        }
    }
}
