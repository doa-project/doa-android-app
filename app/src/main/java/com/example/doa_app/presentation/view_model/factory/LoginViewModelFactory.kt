package com.example.doa_app.presentation.view_model.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.doa_app.domain.usecase.CommonLoginUseCase
import com.example.doa_app.presentation.view_model.LoginViewModel
import com.example.doa_app.utils.SharedPreferences

class LoginViewModelFactory(
    private val commonLoginUseCase: CommonLoginUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(commonLoginUseCase, sharedPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
