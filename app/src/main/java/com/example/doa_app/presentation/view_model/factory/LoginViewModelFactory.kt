package com.example.doa_app.presentation.view_model.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.doa_app.domain.usecase.CommonLoginUseCase
import com.example.doa_app.presentation.view_model.LoginViewModel

class LoginViewModelFactory(
    private val commonLoginUseCase: CommonLoginUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(commonLoginUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
