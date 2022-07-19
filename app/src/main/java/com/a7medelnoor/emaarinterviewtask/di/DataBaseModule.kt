package com.a7medelnoor.emaarinterviewtask.di

import android.content.Context
import androidx.room.Room
import com.a7medelnoor.emaarinterviewtask.data.local.UserDatabase
import com.a7medelnoor.emaarinterviewtask.util.Constants.USER_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    // function to provide database builder class
    @Singleton
    @Provides
    fun provideDataBase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        UserDatabase::class.java,
        USER_DATABASE
    ).build()

    // function to provide database dao
    @Singleton
    @Provides
    fun provideDao(dataBase: UserDatabase) = dataBase.userDao()
}