package com.example.doa_app

import com.example.doa_app.data.datasource.Service
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    // Provide Retrofit instance
    single<Service> {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Service::class.java)
    }

    // Provide Repository
//    single<PostsRepository> { PostsRepositoryImpl(get()) }
//
//    // Provide Use Case
//    single { GetAllPostsUseCase(get()) }
//    single { GetPostUseCase(get()) }
//    single { CreatePostUseCase(get()) }
//    single { UpdatePostUseCase(get()) }
//    single { PatchPostUseCase(get()) }
//    single { DeletePostUseCase(get()) }

    // Provide ViewModel
}
