package com.example.doa_app.presentation.ui.activity

import android.content.ContentValues
import android.content.Intent
import android.content.res.Resources.NotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.doa_app.RetrofitInstance
import com.example.doa_app.data.model.api.Institution
import com.example.doa_app.data.model.api.Login
import com.example.doa_app.data.repository.CommonLoginRepositoryImpl
import com.example.doa_app.databinding.ActivityLoginBinding
import com.example.doa_app.domain.usecase.CommonLoginUseCase
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val commonLoginUseCase =
        CommonLoginUseCase(CommonLoginRepositoryImpl(RetrofitInstance.service))
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

////        ----------------TEST-INSTITUTION-----------------
//        val mockInstitution = Institution(
//            id = 1,
//            name = "Mock Institution",
//            email = "mock@institution.com",
//            description = "This is a description of the mock institution.",
//            local = "123 Mock Street, Mock City",
//            phone = "123-456-7890",
//            photo = null
//        )
////        ----------------TEST-USER-----------------
//        binding.loginButton.setOnClickListener {
//            val intent = Intent(this, HomeInstitutionActivity::class.java)
//            val jsonInstitution = Gson().toJson(mockInstitution)
//            intent.putExtra("loggedUser", jsonInstitution)
//            startActivity(intent)
//            }

//                val mockUser = User(
//            id = 1,
//            name = "Mock Institution",
//            email = "mock@institution.com",
//        )
//
//        binding.loginButton.setOnClickListener {
//            val intent = Intent(this, HomeUserActivity::class.java)
//            val jsonUser = Gson().toJson(mockUser)
//            intent.putExtra("loggedUser", jsonUser)
//            startActivity(intent)
//            }


        binding.loginButton.setOnClickListener {
            lifecycleScope.launch {
                var response: Any
                try {
                    showLoading()
                    response = commonLoginUseCase.login(
                            Login(
                                email = binding.emailForms.text.toString(),
                                password = binding.passwordForms.text.toString()
                            )
                        )
                    var cont: Int
                    if(response.body() == null) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Invalid email or password",
                            Toast.LENGTH_SHORT
                        ).show()
                        hideLoading()
                        return@launch
                    }else{
                        try {
                            val responseJson = gson.toJson(response.body())
                            Log.d("Login", "Institution onCreate: $response")
                            val institution = gson.fromJson(responseJson, Institution::class.java)
                            Log.d("Login", "Institution onCreate: $response")
                            cont = if (institution.institutionId != 0) {
                                2
                            } else {
                                1
                            }
                            if (cont == 1) {
                                val intent =
                                    Intent(this@LoginActivity, HomeUserActivity::class.java)
                                intent.putExtra("loggedUser", responseJson)
                                intent.also {
                                    startActivity(it)
                                }
                            } else {
                                Log.d("Login", "Institution onCreate: $response")
                                val intent =
                                    Intent(this@LoginActivity, HomeInstitutionActivity::class.java)
                                intent.putExtra("loggedUser", responseJson)
                                intent.also {
                                    startActivity(it)
                                }
                                Log.d("Login", "Passed onCreate Institution: $response")
                            }
                        }catch (e: Exception){
                            hideLoading()
                            return@launch
                        }
                    }
                }catch (e: NotFoundException){
                    Log.e(ContentValues.TAG, "NotFoundException, invalid email or password")
                    Toast.makeText(
                        this@LoginActivity,
                        "Invalid email or password",
                        Toast.LENGTH_SHORT
                    ).show()
                    hideLoading()
                    return@launch
                }
                catch (e: TimeoutException){
                    Log.e(ContentValues.TAG, "TimeoutException, request timed out")
                    Toast.makeText(
                        this@LoginActivity,
                        "Request timed out, please try again",
                        Toast.LENGTH_SHORT
                    ).show()
                    hideLoading()
                    return@launch
                }
                catch (e: IOException) {
                    Log.e(ContentValues.TAG, "IOException, you might not have internet connection")
                    Toast.makeText(
                        this@LoginActivity,
                        "You might not have internet connection",
                        Toast.LENGTH_SHORT
                    ).show()
                    hideLoading()
                    return@launch
                }
                catch (e: InternalError){
                    Log.e(ContentValues.TAG, "InternalError, an error occurred")
                    Toast.makeText(this@LoginActivity, "An error occurred", Toast.LENGTH_SHORT)
                        .show()
                    hideLoading()
                    return@launch
                }
                catch (e: Exception) {
                    Log.e(ContentValues.TAG, "Generic Exception, a error occurred")
                    Toast.makeText(this@LoginActivity, "A error occurred", Toast.LENGTH_SHORT)
                        .show()
                    hideLoading()
                    return@launch
                }
            }
        }
    }
    private fun showLoading() {
        binding.progressBar4.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar4.visibility = View.INVISIBLE
    }
}

