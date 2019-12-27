package com.example.todolist_ramkumartextiles.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.todolist_ramkumartextiles.auth.data.repo.AuthRepository
import com.example.todolist_ramkumartextiles.tasks.data.repo.TaskRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application:Application){

    @Provides
    @Singleton
    fun providesAuthRepository(sharedPreferences: SharedPreferences): AuthRepository {
        return AuthRepository(sharedPreferences)
    }

    @Provides
    @Singleton
    fun providesTaskRepository(sharedPreferences: SharedPreferences):TaskRepository{
        return TaskRepository(sharedPreferences)
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

    @Provides
    @Singleton
    fun providesSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("LoginPref", Context.MODE_PRIVATE)
    }
}