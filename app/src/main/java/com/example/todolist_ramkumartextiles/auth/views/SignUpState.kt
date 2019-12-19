package com.example.todolist_ramkumartextiles.auth.views

sealed class SignUpState {
    object Success:SignUpState()
    data class Failure(val message:String):SignUpState()
    object Idle:SignUpState()
    object Loading:SignUpState()
    object moveTo:SignUpState()
}