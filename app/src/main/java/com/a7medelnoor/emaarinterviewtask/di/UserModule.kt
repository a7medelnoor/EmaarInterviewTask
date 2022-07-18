package com.a7medelnoor.emaarinterviewtask.di

import com.a7medelnoor.emaarinterviewtask.data.remote.api.ApiService
import com.a7medelnoor.emaarinterviewtask.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
/**
 ** @author Ahmed Elnoor
 ** Email: ahdnoor4@gmail.com
 ** Github: @a7medelnoor
 ** @since 18/07/2022
 ** This Assessment for Emaar Android developer interview task
 ** IT people Gulf
 ** Don't use this code for commercial propose
 **/
@Module
@InstallIn(SingletonComponent::class)
object UserModule {


    @Provides
    @Singleton
    fun provideRetrofit(): ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
}