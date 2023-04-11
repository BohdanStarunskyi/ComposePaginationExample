package com.example.pagination.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.pagination.common.annotations.DefaultDispatcher
import com.example.pagination.common.annotations.IoDispatcher
import com.example.pagination.common.annotations.MainDispatcher
import com.example.pagination.common.constants.Constants
import com.example.pagination.data.network.API
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideApi(@ApplicationContext context: Context): API {
        return Retrofit.Builder().baseUrl(Constants.API_BASE_URL).client(
            OkHttpClient.Builder()
                .addInterceptor(ChuckerInterceptor.Builder(context).build())
                .build()
        ).addConverterFactory(GsonConverterFactory.create()).build().create(API::class.java)
    }

    @Provides
    @DefaultDispatcher
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @IoDispatcher
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @MainDispatcher
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

}