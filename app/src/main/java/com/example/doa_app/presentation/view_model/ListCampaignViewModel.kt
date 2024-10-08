package com.example.doa_app.presentation.view_model

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doa_app.data.model.api.CampaignAPI
import com.example.doa_app.data.model.mobile.CampaignMob
import com.example.doa_app.data.model.mobile.Image
import com.example.doa_app.domain.usecase.UseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.concurrent.TimeoutException

class ListCampaignViewModel(
    private val useCases: UseCases
) : ViewModel() {

    val campaignMobList = MutableLiveData<List<CampaignMob>>()
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun getAllCampaigns() {
        loading.value = true

        viewModelScope.launch {
            try {
                val response = useCases.getAllCampaign().body()
                if (response.isNullOrEmpty()) {
                    errorMessage.value = "No campaigns found!"
                    Log.e("ListCampaignViewModel", "No publications found")
                } else {
                    Log.d("ListCampaignViewModel", "Response: $response")
                    campaignMobList.value = transformCampaignList(response)
                }
            } catch (e: IOException) {
                Log.e(
                    "ListCampaignViewModel",
                    "IOException, you might not have internet connection: ${e.message}"
                )
                errorMessage.value = "You might not have internet connection"
            } catch (e: TimeoutException) {
                Log.e(
                    "ListCampaignViewModel",
                    "TimeoutException, request timed out: ${e.message}"
                )
                errorMessage.value = "Request timed out"
            } catch (e: Exception) {
                Log.e("ListCampaignViewModel", "Exception, an error occurred: ${e.message}")
                errorMessage.value = "An error occurred"
            } finally {
                loading.value = false
            }
        }
    }

    private suspend fun transformCampaignList(campaignList: List<CampaignAPI>): List<CampaignMob> {
        return withContext(Dispatchers.IO) {
            campaignList.map { campaign ->
                val imageList = campaign.images.mapNotNull { imageUrl ->
                    val bitmap = loadImage(imageUrl)
                    var cont = 0
                    bitmap?.let { Image(cont++.toString(), it) }
                }.toMutableList()

                CampaignMob(
                    campaign.id,
                    campaign.campaignId,
                    campaign.institutionId,
                    campaign.institutionName,
                    campaign.institutionPhoto,
                    campaign.description,
                    imageList,
                    campaign.endDate,
                    campaign.local
                )
            }
        }
    }

    private suspend fun loadImage(url: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            useCases.loadImage(url)
        }
    }
}
