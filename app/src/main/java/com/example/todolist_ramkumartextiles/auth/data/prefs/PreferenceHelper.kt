package com.example.todolist_ramkumartextiles.auth.data.prefs

interface PreferenceHelper{

    fun getUsername():String

    fun setUsername(username:String)

    fun getLoginStatus():Boolean

    fun setLoginStatus(status:Boolean)
}