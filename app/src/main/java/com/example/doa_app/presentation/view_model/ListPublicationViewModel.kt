package com.example.doa_app.presentation.view_model

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doa_app.data.model.mobile.PublicationMob
import com.example.doa_app.data.model.api.PublicationAPI
import com.example.doa_app.domain.usecase.PublicationUseCase
import com.example.doa_app.utils.SharedPreferences
import com.example.doa_app.utils.TreatmentApiObjects
import com.example.doa_app.utils.gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException

class ListPublicationViewModel(
    private val sharedPreferences: SharedPreferences,
    private val publicationUseCase: PublicationUseCase,
    private val treatmentApiObjects: TreatmentApiObjects
) : ViewModel() {

    val publicationMobList = MutableLiveData<List<PublicationMob>>()
    val errorMessage = MutableLiveData<String>()
    val loadingVisibility = MutableLiveData<Int>()
    fun fetchPublications() {
        viewModelScope.launch {
            val localStorage = getLocalStorage()

            if (localStorage != null) {
                val listType = object : TypeToken<List<PublicationMob>>() {}.type
                val publicationList: List<PublicationMob> = gson.fromJson(localStorage, listType)

                publicationMobList.value = publicationList
                return@launch

            } else {
                var response: List<PublicationAPI>?
                try {
                    showLoading()
                    publicationUseCase.getAllPublications().let {
                        response = it.body()
                        if (response.isNullOrEmpty()) {
                            errorMessage.value = "No publications found"
                            Log.e(ContentValues.TAG, "No publications found")
                        } else {
                            Log.d(ContentValues.TAG, "Response: $response")

                            val publicationList = treatmentApiObjects.publicationApiToPublicationList(response!!)
                            Log.d(ContentValues.TAG, "PublicationMobList: $publicationMobList")
                            publicationMobList.value = publicationList
                            saveLocalStorage(gson.toJson(publicationList))
                        }
                    }
                } catch (e: IOException) {
                    Log.e(
                        ContentValues.TAG,
                        "IOException, you might not have internet connection: ${e.message}"
                    )
                    errorMessage.value = "You might not have internet connection"
                } catch (e: TimeoutException) {
                    Log.e(ContentValues.TAG, "TimeoutException, request timed out: ${e.message}")
                    errorMessage.value = "Request timed out"
                } catch (e: Exception) {
                    Log.e(ContentValues.TAG, "Exception, an error occurred: ${e.message}")
                    errorMessage.value = "An error occurred"
                } finally {
                    hideLoading()
                }
            }
        }
    }

    private fun getLocalStorage(): String? {
        val currentPublication = sharedPreferences.getString("currentPublication", "")
        if (!currentPublication.isNullOrEmpty()) {
            return currentPublication
        }
        return null
    }
    private fun saveLocalStorage(currentPublication: String) {
        sharedPreferences.saveString("currentPublication", currentPublication)
    }

    private fun showLoading() {
        loadingVisibility.value = View.VISIBLE
    }

    private fun hideLoading() {
        loadingVisibility.value = View.INVISIBLE
    }
}
