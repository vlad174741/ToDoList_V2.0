package com.example.todolist_v20.objects

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences

object SharedPreference {

    var authUsernamePref = ""
    var prefsAuthUsername: SharedPreferences? = null

    fun preferenceUsername(context: Context) {


        var sharedPreferences =  ContextWrapper(context)

        prefsAuthUsername = sharedPreferences.getSharedPreferences("usernamePref", Context.MODE_PRIVATE)
        authUsernamePref = prefsAuthUsername?.getString("usernamePref", "")!!
    }

    var authEmailPref = ""
    var prefsAuthEmail: SharedPreferences? = null

    fun preferenceEmail(context: Context) {


        var sharedPreferences =  ContextWrapper(context)

        prefsAuthEmail = sharedPreferences.getSharedPreferences("emailPref", Context.MODE_PRIVATE)
        authEmailPref = prefsAuthEmail?.getString("emailPref", "")!!
    }




}