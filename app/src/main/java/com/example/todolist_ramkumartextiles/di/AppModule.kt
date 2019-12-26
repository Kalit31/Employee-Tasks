package com.example.todolist_ramkumartextiles.di

import android.app.Application
import android.content.Context
import com.example.todolist_ramkumartextiles.auth.data.prefs.AuthPreferenceHelper
import com.example.todolist_ramkumartextiles.auth.data.repo.AuthRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application:Application){

    @Provides
    @Singleton
    fun providesAuthRepository(authPreferenceHelper: AuthPreferenceHelper): AuthRepository {
        return AuthRepository(authPreferenceHelper)
    }

    @Provides
    @Singleton
    fun providesApplication():Application{
        return application
    }

    @Provides
    fun provideContext(): Context {
        return application
    }
}