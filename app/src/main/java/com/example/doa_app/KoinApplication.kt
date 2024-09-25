package com.example.doa_app

import android.content.Context
import com.example.doa_app.data.datasource.Service
import com.example.doa_app.data.firebase.FirebaseStorageManager
import com.example.doa_app.data.repository.RepositoryImpl
import com.example.doa_app.domain.repository.Repository
import com.example.doa_app.domain.usecase.UseCases
import com.example.doa_app.presentation.view_model.AddPublicationViewModel
import com.example.doa_app.presentation.view_model.ListCampaignOfInstitutionViewModel
import com.example.doa_app.presentation.view_model.ListCampaignViewModel
import com.example.doa_app.presentation.view_model.LoginViewModel
import com.example.doa_app.utils.SharedPreferences
import com.example.doa_app.utils.gson
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<Service> {
        Retrofit.Builder()
            .baseUrl("https://doa-api-1.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(Service::class.java)
    }
    single {
        FirebaseStorageManager()
    }
    single<Repository> {
        RepositoryImpl(get(), get())
    }

    single {
        UseCases(get())
    }

    single {
        SharedPreferences(get(), "doa_app_cache")
    }

    single {
        get<Context>().getSharedPreferences(
        "doa_app_cache", Context.MODE_PRIVATE
        )
    }

    viewModel {
        AddPublicationViewModel(
            useCases = get()
        )
    }

    viewModel {
        LoginViewModel(
            useCases = get(),
            sharedPreferences = get()
        )
    }

    viewModel {
        ListCampaignViewModel(
            useCases = get(),
            sharedPreferences = get()
        )
    }

    viewModel {
        ListCampaignOfInstitutionViewModel(
            useCases = get(),
            sharedPreferences = get()
        )
    }
}
