package com.example.todolist_v20.classes

import android.content.*
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist_v20.objects.SharedPreference
import com.example.todolist_v20.objects.ToastText
import com.example.todolist_v20.objects.Variable
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
        SharedPreference.preferenceUsername(this)
        SharedPreference.preferenceEmail(this)
        checkBiometric()


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

            buttonFingerPrint.setOnClickListener {
                fingerPrintDialog()
            }
        }





    }




public override fun onResume() {
        super.onResume()
    }



    private fun checkBiometric(){

        if(packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)){

            bindingAuth.buttonFingerPrint.visibility = View.VISIBLE

        }else{

            bindingAuth.buttonFingerPrint.visibility = View.VISIBLE

        }

    }

    private fun fingerPrintDialog(){


             val prompt = BiometricPrompt.Builder(this)
            .setTitle("Авторизация по отпечатку пальца")
            .setDescription("Используйте свой отпечаток для авторизации")
            .setNegativeButton("Отмена", mainExecutor) { _, _ -> }
                 .build()

        prompt.authenticate(getCancellationSignal(),mainExecutor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(this@AuthClass, "Провал" , Toast.LENGTH_LONG).show()


                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)

                    if (errorCode == 11) {
                        Toast.makeText(
                            this@AuthClass,
                            "Отпечаток не найден",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)

                        fingerPrintAction()


                }
            }
        )

    }

    fun fingerPrintAction(){
        val intentMainActivity = Intent(this@AuthClass, MainActivity::class.java)




        Variable.username = SharedPreference.authUsernamePref
        Variable.email = SharedPreference.authEmailPref

        ToastText.longToast(this,"Здравствуйте ${Variable.username}")
        Log.d("id", SharedPreference.authUsernamePref)
        finish()
        startActivity(intentMainActivity)
    }

    private fun getCancellationSignal(): CancellationSignal {

        val cancelSignal = CancellationSignal()
        cancelSignal.setOnCancelListener {

        }
        return cancelSignal
    }


    override fun onDestroy() {
        super.onDestroy()

        dbManagerAuth.closeDataBase()
    }

}