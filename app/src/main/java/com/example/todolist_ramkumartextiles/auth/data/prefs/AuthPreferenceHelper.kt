package com.example.todolist_ramkumartextiles.auth.data.prefs

import android.content.Context
import android.content.SharedPreferences
import android.content.Context.MODE_PRIVATE
import dagger.Provides
import javax.inject.Inject

class AuthPreferenceHelper @Inject constructor(context: Context) :PreferenceHelper{

    companion object{
        public val PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME"

        public val PREF_KEY_LOGIN_STATUS = "PREF_KEY_LOGIN_STATUS"
    }


    private var mPrefs: SharedPreferences? = null

    private val prefFileName = "LoginPref"

    init {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
    }

    override fun getUsername(): String {
        return mPrefs?.getString(PREF_KEY_CURRENT_USER_NAME,null).toString()
    }

    override fun setUsername(username: String) {
        mPrefs?.edit()?.putString(PREF_KEY_CURRENT_USER_NAME,username)?.apply()
    }

    override fun getLoginStatus(): Boolean {
        return mPrefs?.getBoolean(PREF_KEY_LOGIN_STATUS,false)!!
    }

    override fun setLoginStatus(status: Boolean) {
        mPrefs?.edit()?.putBoolean(PREF_KEY_LOGIN_STATUS,status)?.apply()
    }

}