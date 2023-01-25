package com.example.todolist_v20.classes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist_v20.dataBase.dbAuthorization.DataBaseManagerAuth
import com.example.todolist_v20.objects.SharedPreference
import com.example.todolist_v20.objects.Variable

open class BasicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Variable.dbManagerAuth = DataBaseManagerAuth(this)
        checkUser()

    }

    private fun checkUser(){

        SharedPreference.preferenceUsername(this)
        Variable.dbManagerAuth.openDataBase()
        Variable.dbManagerAuth.checkAccount()

        when(Variable.prefTheme){
            0-> ChangeTheme().themeChange(0, delegate)
            1-> ChangeTheme().themeChange(1, delegate)
            2-> ChangeTheme().themeChange(2, delegate)
        }

        if (Variable.auth){
            finish()
            val intentMainActivity = Intent(this, MainActivity::class.java)
            startActivity(intentMainActivity)
            Log.d("liveActivity", "BasicActivity.open.MainActivity")

        }else{
            finish()
            val intentAuthClass = Intent(this, AuthClass::class.java)
            startActivity(intentAuthClass)
            Log.d("liveActivity", "BasicActivity.open.AuthClass")

        }
    }


}