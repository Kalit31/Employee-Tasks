package com.example.todolist_ramkumartextiles.di

import android.app.Application
import com.example.todolist_ramkumartextiles.auth.data.AuthRepository
import com.example.todolist_ramkumartextiles.auth.data.FirebaseSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application:Application){

    @Provides
    @Singleton
    fun providesAuthRepository(firebaseSource:FirebaseSource):AuthRepository{
        return AuthRepository(firebaseSource)
    }

    @Provides
    @Singleton
    fun providesApplication():Application{
        return application
    }
}