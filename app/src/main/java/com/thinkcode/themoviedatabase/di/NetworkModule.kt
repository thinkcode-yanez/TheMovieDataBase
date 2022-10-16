package com.thinkcode.themoviedatabase.di

import com.thinkcode.themoviedatabase.core.Constants
import com.thinkcode.themoviedatabase.data.network.ApiNetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiNetworkService(retrofit: Retrofit):ApiNetworkService{

        return retrofit.create(ApiNetworkService::class.java)

    }

}