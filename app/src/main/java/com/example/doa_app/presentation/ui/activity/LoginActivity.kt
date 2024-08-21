package com.example.doa_app.presentation.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.doa_app.RetrofitInstance
import com.example.doa_app.data.repository.CommonLoginRepositoryImpl
import com.example.doa_app.databinding.ActivityLoginBinding
import com.example.doa_app.domain.usecase.CommonLoginUseCase
import com.example.doa_app.presentation.view_model.LoginViewModel
import com.example.doa_app.presentation.view_model.factory.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels{
        LoginViewModelFactory(
            CommonLoginUseCase(CommonLoginRepositoryImpl(RetrofitInstance.service))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel.loadingVisibility.observe(this) {
            binding.progressBar4.visibility = it
        }

        loginViewModel.errorMessage.observe(this) { message ->
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }

        loginViewModel.loginSuccess.observe(this) { (success, intent) ->
            if (success && intent != null) {
                startActivity(intent)
            }
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailForms.text.toString()
            val password = binding.passwordForms.text.toString()
            loginViewModel.login(email, password)
        }
    }
}
