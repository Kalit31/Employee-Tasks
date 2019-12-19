package com.example.todolist_ramkumartextiles

import android.app.Application
import com.example.todolist_ramkumartextiles.di.AppComponent
import com.example.todolist_ramkumartextiles.di.AppModule
import com.example.todolist_ramkumartextiles.di.DaggerAppComponent
import javax.inject.Inject

class EmployeeApp : Application(){
    companion object{
        lateinit var appComponent:AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}