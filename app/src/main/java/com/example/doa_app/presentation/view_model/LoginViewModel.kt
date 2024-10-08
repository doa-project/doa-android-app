package com.example.doa_app.presentation.view_model

import android.content.ContentValues
import android.content.Intent
import android.content.res.Resources.NotFoundException
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doa_app.data.model.api.Institution
import com.example.doa_app.data.model.api.Login
import com.example.doa_app.data.model.api.User
import com.example.doa_app.domain.usecase.UseCases
import com.example.doa_app.utils.SharedPreferences
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException

class LoginViewModel(
    private val useCases: UseCases,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val gson = Gson()

    val loginSuccess = MutableLiveData<Pair<Boolean, Intent>>()
    val loadingVisibility = MutableLiveData<Int>()
    val errorMessage = MutableLiveData<String>()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val response: Any
            try {
                showLoading()
                response = useCases.login(Login(email = email, password = password))
                if (response.body() == null) {
                    errorMessage.value = "Email ou senha inválidos"
                    hideLoading()
                    return@launch
                } else {
                    try {
                        val responseJson = gson.toJson(response.body())
                        Log.d("Login", "Institution onCreate: $response")
                        val institution = gson.fromJson(responseJson, Institution::class.java)
                        Log.d("Login", "Institution onCreate: $response")
                        val intent: Intent
                        if (institution.institutionId != 0) {
                            val user = User(
                                institution.id,
                                institution.institutionId,
                                institution.name,
                                institution.email
                            )
                            saveUserLocalStorage(responseJson)
                            saveInstitutionLocalStorage(responseJson)
                            intent = Intent(Intent.ACTION_MAIN).apply {
                                setClassName(
                                    "com.example.doa_app",
                                    "com.example.doa_app.presentation.ui.activity.HomeInstitutionActivity"
                                )
                            }
                        } else {
                            saveUserLocalStorage(responseJson)
                            intent = Intent(Intent.ACTION_MAIN).apply {
                                setClassName(
                                    "com.example.doa_app",
                                    "com.example.doa_app.presentation.ui.activity.HomeUserActivity"
                                )
                            }
                        }
                        loginSuccess.value = Pair(true, intent)
                        hideLoading()
                        return@launch
                    } catch (e: Exception) {
                        errorMessage.value = "Um erro aconteceu"
                        hideLoading()
                        return@launch
                    }
                }
            } catch (e: NotFoundException) {
                Log.e(ContentValues.TAG, "NotFoundException, invalid email or password")
                errorMessage.value = "Email ou senha inválidos"
                hideLoading()
                return@launch
            } catch (e: TimeoutException) {
                Log.e(ContentValues.TAG, "TimeoutException, request timed out")
                errorMessage.value = "Tente de novo em 2 minutos"
                hideLoading()
                return@launch
            } catch (e: IOException) {
                Log.e(ContentValues.TAG, "IOException, you might not have internet connection")
                errorMessage.value = "Tente de novo em 2 minutos"
                hideLoading()
                return@launch
            } catch (e: InternalError) {
                Log.e(ContentValues.TAG, "InternalError, an error occurred")
                errorMessage.value = "Algum erro ocorreu"
                hideLoading()
                return@launch
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Generic Exception, an error occurred")
                errorMessage.value = "Algum erro ocorreu"
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
