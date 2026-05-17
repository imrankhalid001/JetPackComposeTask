package com.imidroid.jetpackcomposetask.di


import com.imidroid.jetpackcomposetask.data.remoteapi.ProductApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.getValue

@Module
@InstallIn(SingletonComponent::class)
object ProductRetrofitInstance {

    private const val BASE_URL = "https://fakestoreapi.com//"

    @get:Provides
    @Singleton
    val api : ProductApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApiService::class.java)
    }
}

/*
Dagger Hilt is managing everything behind the scenes as a constructor :
    - ProductUI ---> ProductViewModel ----> ProductRepository ----> ProductApiService ----> ProductApiService is created inside ProductRetrofitInstance
*/
