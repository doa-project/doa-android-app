package com.example.doa_app.presentation.view_model

import android.content.ContentValues
import android.content.Intent
import android.content.res.Resources.NotFoundException
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doa_app.RetrofitInstance
import com.example.doa_app.data.model.api.Institution
import com.example.doa_app.data.model.api.Login
import com.example.doa_app.data.repository.CommonLoginRepositoryImpl
import com.example.doa_app.domain.usecase.CommonLoginUseCase
import com.example.doa_app.utils.SharedPreferences
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException

class LoginViewModel(
    private val commonLoginUseCase: CommonLoginUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val gson = Gson()

    val loginSuccess = MutableLiveData<Pair<Boolean, Intent?>>()
    val loadingVisibility = MutableLiveData<Int>()
    val errorMessage = MutableLiveData<String>()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            var response: Any
            try {
                showLoading()
                response = commonLoginUseCase.login(Login(email = email, password = password))
                if (response.body() == null) {
                    errorMessage.value = "Invalid email or password"
                    hideLoading()
                    return@launch
                } else {
                    try {
                        val responseJson = gson.toJson(response.body())
                        Log.d("Login", "Institution onCreate: $response")
                        val institution = gson.fromJson(responseJson, Institution::class.java)
                        Log.d("Login", "Institution onCreate: $response")
                        val intent = if (institution.institutionId != 0) {
                            Intent(Intent.ACTION_MAIN).apply {
                                setClassName(
                                    "com.example.doa_app",
                                    "com.example.doa_app.presentation.ui.activity.HomeInstitutionActivity"
                                )
                                putExtra("loggedUser", responseJson)
                            }
                        } else {
                            Intent(Intent.ACTION_MAIN).apply {
                                setClassName(
                                    "com.example.doa_app",
                                    "com.example.doa_app.presentation.ui.activity.HomeUserActivity"
                                )
                                putExtra("loggedUser", responseJson)
                            }
                        }
                        loginSuccess.value = Pair(true, intent)
                    } catch (e: Exception) {
                        errorMessage.value = "An error occurred"
                        hideLoading()
                        return@launch
                    }
                }
            } catch (e: NotFoundException) {
                Log.e(ContentValues.TAG, "NotFoundException, invalid email or password")
                errorMessage.value = "Invalid email or password"
                hideLoading()
                return@launch
            } catch (e: TimeoutException) {
                Log.e(ContentValues.TAG, "TimeoutException, request timed out")
                errorMessage.value = "Request timed out, please try again"
                hideLoading()
                return@launch
            } catch (e: IOException) {
                Log.e(ContentValues.TAG, "IOException, you might not have internet connection")
                errorMessage.value = "You might not have internet connection"
                hideLoading()
                return@launch
            } catch (e: InternalError) {
                Log.e(ContentValues.TAG, "InternalError, an error occurred")
                errorMessage.value = "An error occurred"
                hideLoading()
                return@launch
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Generic Exception, an error occurred")
                errorMessage.value = "An error occurred"
                hideLoading()
                return@launch
            }
        }
    }


    private fun saveInstitutionLocalStorage(currentInstitution: String) {
        sharedPreferences.saveString("loggedInstitution", currentInstitution)
    }
    private fun saveUserLocalStorage(currentUser: String) {
        sharedPreferences.saveString("loggedUser", currentUser)
    }

    private fun showLoading() {
        loadingVisibility.value = View.VISIBLE
    }

    private fun hideLoading() {
        loadingVisibility.value = View.INVISIBLE
    }
}
