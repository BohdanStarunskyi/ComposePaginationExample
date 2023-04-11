package com.example.apparchitecture.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.apparchitecture.common.annotations.DefaultDispatcher
import com.example.apparchitecture.common.annotations.IoDispatcher
import com.example.apparchitecture.common.annotations.MainDispatcher
import com.example.apparchitecture.common.constants.Constants
import com.example.apparchitecture.data.network.API
import com.example.apparchitecture.domain.interceptors.ApiTokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideApi(@ApplicationContext context: Context, interceptor: ApiTokenInterceptor): API {
        return Retrofit.Builder().baseUrl(Constants.API_BASE_URL).client(
            OkHttpClient.Builder()
                .addInterceptor(ChuckerInterceptor.Builder(context).build())
                .addInterceptor(interceptor)
                .build()
        ).addConverterFactory(GsonConverterFactory.create()).build().create(API::class.java)
    }

    @Singleton
    @Provides
    fun provideDataPreferences(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(corruptionHandler = ReplaceFileCorruptionHandler(
            produceNewData = { emptyPreferences() }),
            migrations = listOf(SharedPreferencesMigration(context, Constants.PREFERENCES_NAME)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { context.preferencesDataStoreFile(Constants.PREFERENCES_NAME) })

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