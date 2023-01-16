package com.example.todolist_v20.objects

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences

object SharedPreference {

    var authUsernamePref = ""
    var prefsAuthUsername: SharedPreferences? = null

    fun preferenceUsername(context: Context) {


        val sharedPreferences =  ContextWrapper(context)

        prefsAuthUsername = sharedPreferences.getSharedPreferences("username", Context.MODE_PRIVATE)
        authUsernamePref = prefsAuthUsername?.getString("username", "user")!!
    }

    var authThemePref = ""
    var prefsTheme: SharedPreferences? = null

    fun preferenceTheme(context: Context) {


        val sharedPreferences =  ContextWrapper(context)

        prefsTheme = sharedPreferences.getSharedPreferences("themePref", Context.MODE_PRIVATE)
        authThemePref = prefsTheme?.getString("themePref", "")!!
    }




}