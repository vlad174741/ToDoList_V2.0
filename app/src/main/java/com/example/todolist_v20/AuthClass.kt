package com.example.todolist_v20

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist_v20.dataBase.dbAuthorization.DataBaseManagerAuth
import com.example.todolist_v20.databinding.ActivityAuthBinding

lateinit var bindingAuth:ActivityAuthBinding

class AuthClass: AppCompatActivity() {

    private val dbManagerAuth = DataBaseManagerAuth(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingAuth = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(bindingAuth.root)

        dbManagerAuth.openDataBase()


        bindingAuth.apply {



            buttonAuth.setOnClickListener {
                var email = editTextAuthEmail.text.toString()
                var username = editTextAuthUsername.text.toString()
                val password = editTextAuthPassword.text.toString()


                if (email.isEmpty() && username.isEmpty() && password.isEmpty()){
                    Toast.makeText(this@AuthClass, "Заполните поля для регистрации", Toast.LENGTH_SHORT).show()

                }else {

                    if (username.length < 4) {
                        Toast.makeText(
                            this@AuthClass,
                            "Введите не менее 4 символов для логина",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        if (password.length < 4) {
                            Toast.makeText(
                                this@AuthClass,
                                "Введите не менее 4 символов для пароля",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (email.isEmpty()) {
                                email = username
                            }
                            if (username.isEmpty()) {
                                username = email
                            }

                            dbManagerAuth.checkAccountsForRegistration(username,email,password)


                        }

                    }
                }
            }

            fun View.hideKeyboard() {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(windowToken, 0)
            }

            buttonAuthSingIn.setOnClickListener {

                val intentMainActivity = Intent(this@AuthClass, MainActivity::class.java)
                val email = editTextAuthEmail.text.toString()
                val username = editTextAuthUsername.text.toString()
                val password = editTextAuthPassword.text.toString()

                dbManagerAuth.findAccountId(username,email,password)
                it.hideKeyboard()


                if (Variable.auth){
                    finish()
                    startActivity(intentMainActivity)
                }

            }
        }





    }



public override fun onResume() {
        super.onResume()
    }



    override fun onDestroy() {
        super.onDestroy()

        dbManagerAuth.closeDataBase()
    }

}