package com.example.todolist_v20

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.view.inputmethod.InputMethodManager


class SaveData {

    fun saveDataString(res:String,pref:SharedPreferences?=null,key:String){

        val editorAuth = pref?.edit()
        editorAuth?.putString(key, res)
        editorAuth?.apply()

    }

    fun saveDataInt(res:Int,pref:SharedPreferences?=null,key:String){

        val editorAuth = pref?.edit()
        editorAuth?.putInt(key, res)
        editorAuth?.apply()


    }



}