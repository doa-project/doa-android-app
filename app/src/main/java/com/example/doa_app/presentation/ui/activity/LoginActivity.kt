package com.example.doa_app.presentation.ui.activity

import android.content.ContentValues
import android.content.Intent
import android.net.http.HttpException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.doa_app.RetrofitInstance
import com.example.doa_app.data.model.Institution
import com.example.doa_app.data.model.Login
import com.example.doa_app.data.model.User
import com.example.doa_app.data.repository.CommonLoginRepositoryImpl
import com.example.doa_app.databinding.ActivityLoginBinding
import com.example.doa_app.domain.usecase.CommonLoginUseCase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val commonLoginRepository = CommonLoginRepositoryImpl(RetrofitInstance.service)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginButton.setOnClickListener {
            val intent= Intent(this@LoginActivity, HomeInstitutionActivity::class.java)
            intent.also {
                startActivity(it)
            }
        }

//        binding.loginButton.setOnClickListener {
//            lifecycleScope.launchWhenCreated {
//                try {
//                    val response =
//                        CommonLoginUseCase(commonLoginRepository).login(
//                            Login(
//                                email = binding.emailForms.text.toString(),
//                                password = binding.passwordForms.text.toString()
//                            )
//                        )
//                    if (response.body() is User) {
//                        val intent= Intent(this@LoginActivity, HomeInstitutionActivity::class.java)
//                        intent.putExtra("loggedUser", response.toString())
//                        intent.also {
//                            startActivity(it)
//                        }
//                    }
//                    else if (response.body() is Institution) {
//                        val intent= Intent(this@LoginActivity, HomeUserActivity::class.java)
//                        intent.putExtra("loggedUser", response.toString())
//                        intent.also {
//                            startActivity(it)
//                        }
//                    }
//                } catch (e: IllegalAccessException) {
//                    Log.e(ContentValues.TAG, "IllegalAccessException, invalid email or password")
//                    Toast.makeText(
//                        this@LoginActivity,
//                        "Invalid email or password",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return@launchWhenCreated
//                } catch (e: IOException) {
//                    Log.e(ContentValues.TAG, "IOException, you might not have internet connection")
//                    Toast.makeText(
//                        this@LoginActivity,
//                        "You might not have internet connection",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return@launchWhenCreated
//                } catch (e: HttpException) {
//                    Log.e(
//                        ContentValues.TAG,
//                        "HttpResponseException, you might not have internet connection"
//                    )
//                    Toast.makeText(
//                        this@LoginActivity,
//                        "You might not have internet connection",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return@launchWhenCreated
//                } catch (e: Exception) {
//                    Log.e(ContentValues.TAG, "Generic Exception, a error occurred")
//                    Toast.makeText(this@LoginActivity, "A error occurred", Toast.LENGTH_SHORT)
//                        .show()
//                    return@launchWhenCreated
//                }
//            }
//        }
    }
}
