package com.example.doa_app.presentation.ui.activity

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.doa_app.RetrofitInstance
import com.example.doa_app.appModule
import com.example.doa_app.data.model.Login
import com.example.doa_app.data.repository.CommonLoginRepositoryImpl
import com.example.doa_app.databinding.ActivityLoginBinding
import com.example.doa_app.domain.repository.CommonLoginRepository
import com.example.doa_app.domain.usecase.CommonLoginUseCase
import com.example.doa_app.domain.usecase.UserUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import retrofit2.HttpException
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val commonLoginRepository = CommonLoginRepositoryImpl(RetrofitInstance.service)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalContext.startKoin {
            androidContext(this@LoginActivity)
            modules(appModule)
        }

        binding.loginButton.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                val response = try {
                    CommonLoginUseCase(commonLoginRepository).login(
                        Login(
                            email = binding.emailForms.text.toString(),
                            password = binding.passwordForms.text.toString()
                        )
                    )
                } catch (e: IllegalAccessException) {
                    Log.e(ContentValues.TAG, "IllegalAccessException, invalid email or password")
                    Toast.makeText(
                        this@LoginActivity,
                        "Invalid email or password",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launchWhenCreated
                } catch (e: IOException) {
                    Log.e(ContentValues.TAG, "IOException, you might not have internet connection")
                    Toast.makeText(
                        this@LoginActivity,
                        "You might not have internet connection",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launchWhenCreated
                } catch (e: HttpException) {
                    Log.e(
                        ContentValues.TAG,
                        "HttpResponseException, you might not have internet connection"
                    )
                    Toast.makeText(
                        this@LoginActivity,
                        "You might not have internet connection",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launchWhenCreated
                } catch (e: Exception) {
                    Log.e(ContentValues.TAG, "Generic Exception, a error occurred")
                    Toast.makeText(this@LoginActivity, "A error occurred", Toast.LENGTH_SHORT)
                        .show()
                    return@launchWhenCreated
                }

                if (response.isSuccessful && response.body() != null) {
                    Log.d(ContentValues.TAG, "Login successful")

                    Intent(this@LoginActivity, HomeUserActivity::class.java).also {
                        startActivity(it)
                    }
                } else {
                    Log.e(ContentValues.TAG, "Login failed")
                }
            }
        }
    }
}
