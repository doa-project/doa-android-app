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
import com.example.doa_app.utils.SharedPreferences
import com.example.doa_app.utils.gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.concurrent.TimeoutException

class ListCampaignViewModel(
    private val useCases: UseCases,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    val campaignMobList = MutableLiveData<List<CampaignMob>>()
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun getAllCampaigns() {
        loading.value = true
        val localStorage = getLocalStorage()

        if (localStorage != null) {
            val listType = object : TypeToken<List<CampaignMob>>() {}.type
            val campaignList: List<CampaignMob> = gson.fromJson(localStorage, listType)

            campaignMobList.value = campaignList
            loading.value = false
        } else {
            viewModelScope.launch {
                try {
                    val response = useCases.getAllCampaign().body()
                    if (response.isNullOrEmpty()) {
                        errorMessage.value = "No campaigns found!"
                        Log.e("ListCampaignViewModel", "No publications found")
                    } else {
                        Log.d("ListCampaignViewModel", "Response: $response")
                        campaignMobList.value = transformCampaignList(response)
                        saveLocalStorage(gson.toJson(campaignMobList.value))
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
    }

    // Updated suspend function to transform the campaign list
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

    // Example suspend function to load images
    private suspend fun loadImage(url: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            // Assume repository.loadImage() fetches the Bitmap from the URL
            useCases.loadImage(url)
        }
    }

    private fun getLocalStorage(): String? {
        val currentPublication = sharedPreferences.getString("currentCampaign", "")
        if (!currentPublication.isNullOrEmpty()) {
            return currentPublication
        }
        return null
    }

    private fun saveLocalStorage(currentPublication: String) {
        sharedPreferences.saveString("currentCampaign", currentPublication)
    }
}
