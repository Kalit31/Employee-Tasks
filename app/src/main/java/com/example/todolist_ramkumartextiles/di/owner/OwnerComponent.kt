package com.example.todolist_ramkumartextiles.di.owner

import com.example.todolist_ramkumartextiles.owners.viewmodel.*
import dagger.Subcomponent

@Subcomponent(modules = [OwnerModule::class])
interface OwnerComponent{
    fun inject(loginViewModel: LoginViewModel)

    fun inject(factory: AssignTaskViewModelFactory)

    fun inject(factory: EmployeeInfoViewModelFactory)

    fun inject(factory: StatusViewModelFactory)

    fun inject(factory: MapsViewModelFactory)
}