package com.example.todolist_v20.classes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist_v20.R
import com.example.todolist_v20.dataBase.dbAuthorization.DataBaseManagerAuth
import com.example.todolist_v20.databinding.AuthPinFormBinding
import com.example.todolist_v20.fragments.bindingMainFragment
import com.example.todolist_v20.fragments.rcAdapter
import com.example.todolist_v20.objects.FingerPrint
import com.example.todolist_v20.objects.SharedPreference
import com.example.todolist_v20.objects.Variable


lateinit var bindingAuth:AuthPinFormBinding
lateinit var main:Intent

class AuthClass: AppCompatActivity() {

    private val dbManagerAuth = DataBaseManagerAuth(this)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        main = Intent(this, MainActivity::class.java)

        checkUser()


        bindingAuth = AuthPinFormBinding.inflate(layoutInflater)
        setContentView(bindingAuth.root)

        dbManagerAuth.openDataBase()
        SharedPreference.preferenceUsername(this)
        if (Variable.passwordCheck) {
            bindingAuth.floatingActionButtonOk.foreground =
                resources.getDrawable(R.drawable.ic_number_button_yes)
        }


        // checkBiometric()


        bindingAuth.apply {
            floatingActionButton1.setOnClickListener(@Suppress("UNUSED_PARAMETER") View.OnClickListener
            { textViewPin.append("1"); pinSingIn() })

            floatingActionButton2.setOnClickListener(@Suppress("UNUSED_PARAMETER") View.OnClickListener
            { textViewPin.append("2"); pinSingIn() })

            floatingActionButton3.setOnClickListener(@Suppress("UNUSED_PARAMETER") View.OnClickListener
            { textViewPin.append("3"); pinSingIn() })

            floatingActionButton4.setOnClickListener(@Suppress("UNUSED_PARAMETER") View.OnClickListener
            { textViewPin.append("4"); pinSingIn() })

            floatingActionButton5.setOnClickListener(@Suppress("UNUSED_PARAMETER") View.OnClickListener
            { textViewPin.append("5"); pinSingIn() })

            floatingActionButton6.setOnClickListener(@Suppress("UNUSED_PARAMETER") View.OnClickListener
            { textViewPin.append("6"); pinSingIn() })

            floatingActionButton7.setOnClickListener(@Suppress("UNUSED_PARAMETER") View.OnClickListener
            { textViewPin.append("7"); pinSingIn() })

            floatingActionButton8.setOnClickListener(@Suppress("UNUSED_PARAMETER") View.OnClickListener
            { textViewPin.append("8"); pinSingIn() })

            floatingActionButton9.setOnClickListener(@Suppress("UNUSED_PARAMETER") View.OnClickListener
            { textViewPin.append("9"); pinSingIn() })

            floatingActionButton0.setOnClickListener(@Suppress("UNUSED_PARAMETER") View.OnClickListener
            { textViewPin.append("0"); pinSingIn() })

            floatingActionButtonNo.setOnClickListener(@Suppress("UNUSED_PARAMETER") View.OnClickListener
            {
                if (textViewPin.text.isNotEmpty())
                {
                    val back = textViewPin.text.toString()
                    textViewPin.text = back.substring(0, back.length - 1)

                }else{textViewPin.append("")}

            })

            floatingActionButtonOk.setOnClickListener(@Suppress("UNUSED_PARAMETER") View.OnClickListener
            {
                if (Variable.passwordCheck){
                    if (textViewPin.length() == 4) {
                        Variable.password = textViewPin.text.toString()
                        Variable.passwordCheck = false
                        dbManagerAuth.insertOptionToDB()
                        bindingAuth.floatingActionButtonOk.foreground =
                            resources.getDrawable(R.drawable.ic_baseline_fingerprint)
                        textViewPin.text = ""
                    }else{
                        Toast.makeText(this@AuthClass, "Введите не менее 4 цифр", Toast.LENGTH_SHORT).show()

                    }

                }else { FingerPrint.fingerPrintDialog(this@AuthClass) }

            })

        }
    }






    public override fun onResume() {
        super.onResume()

    }

    fun pinSingIn(){
        if(!Variable.passwordCheck) {
            if (bindingAuth.textViewPin.length() == 4) {

                if (bindingAuth.textViewPin.text.toString() == Variable.password) {
                    startActivity(main)
                    finish()
                    Variable.password = "empty"
                } else {
                    bindingAuth.textViewPin.text = ""
                }
            }
        }
    }



    fun checkUser(){
        SharedPreference.preferenceUsername(this)
        dbManagerAuth.openDataBase()
        dbManagerAuth.checkAccount(this)

        when(Variable.prefTheme){
            0-> ChangeTheme().themeChange(0, delegate)
            1-> ChangeTheme().themeChange(1, delegate)
            2-> ChangeTheme().themeChange(2, delegate)
        }


        if (Variable.auth){
            startActivity(main)
        }
        dbManagerAuth.closeDataBase()
    }

    // private fun checkBiometric(){ if(packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)){ }else{ } }

    override fun onStop() {
        super.onStop()
        if (Variable.auth) {
            finish()
        }
    }





}