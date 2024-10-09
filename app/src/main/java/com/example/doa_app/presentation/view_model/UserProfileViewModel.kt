package com.example.doa_app.presentation.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doa_app.data.model.api.User
import com.example.doa_app.utils.SharedPreferences
import com.example.doa_app.utils.gson

class UserProfileViewModel(
    sharedPreferences: SharedPreferences
): ViewModel(){
    private val _user = MutableLiveData<User>()
    val user = _user

    init {
        _user.value = gson.fromJson(sharedPreferences.getString("loggedUser"), User::class.java)
    }

}