package com.example.todolist_ramkumartextiles.auth.viewmodel

// To fire callbacks

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message:String)
}