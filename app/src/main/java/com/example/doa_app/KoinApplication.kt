package com.example.doa_app

import com.example.doa_app.data.datasource.Service
import com.example.doa_app.data.repository.RepositoryImpl
import com.example.doa_app.domain.repository.Repository
import com.example.doa_app.domain.usecase.UseCases
import com.example.doa_app.presentation.view_model.AddPublicationViewModel
import com.example.doa_app.presentation.view_model.ListCampaignOfInstitutionViewModel
import com.example.doa_app.presentation.view_model.ListCampaignViewModel
import com.example.doa_app.presentation.view_model.ListPublicationOfInstitutionViewModel
import com.example.doa_app.presentation.view_model.ListPublicationViewModel
import com.example.doa_app.presentation.view_model.LoginViewModel
import com.example.doa_app.utils.TreatmentApiObjects
import com.example.doa_app.utils.gson
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<Service> {
        Retrofit.Builder()
            .baseUrl("https://doa-api.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(Service::class.java)
    }
    factory {
        TreatmentApiObjects()
    }
    single<Repository> {
        RepositoryImpl(get())
    }

    single {
        UseCases(get())
    }

    viewModel{
        LoginViewModel(get(), get())
        AddPublicationViewModel(get(), get())
        ListCampaignViewModel(get(), get(), get())
        ListPublicationViewModel(get(), get(), get())
        ListCampaignOfInstitutionViewModel()
        ListPublicationOfInstitutionViewModel()
    }
}
