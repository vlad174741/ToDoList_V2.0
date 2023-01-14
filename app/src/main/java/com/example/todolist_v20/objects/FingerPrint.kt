package com.example.todolist_v20.objects

import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.content.Intent
import android.hardware.biometrics.BiometricPrompt
import android.os.CancellationSignal
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.todolist_v20.classes.AuthClass
import com.example.todolist_v20.classes.MainActivity
import com.example.todolist_v20.classes.SaveData
import com.example.todolist_v20.classes.main

object FingerPrint {

    fun fingerPrintDialog(context: Context) {

        val mainExecutor = ContextWrapper(context)


        val prompt = BiometricPrompt.Builder(context)
            .setTitle("Авторизация по отпечатку пальца")
            .setDescription("Используйте свой отпечаток для авторизации")
            .setNegativeButton("Отмена", mainExecutor.mainExecutor,
                DialogInterface.OnClickListener{ _, _-> })
            .build()

        prompt.authenticate(
            getCancellationSignal(),mainExecutor.mainExecutor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(context, "Провал" , Toast.LENGTH_LONG).show()


                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)



                    if (errorCode == 11) {
                        Toast.makeText(
                            context,
                            "Отпечаток не найден",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    var main = Intent(context, MainActivity::class.java)
                    ContextCompat.startActivity(context,main, null)
                    Variable.auth=true
                    Variable.password = "empty"





                }
            }
        )


    }

    fun getCancellationSignal(): CancellationSignal {

        val cancelSignal = CancellationSignal()
        cancelSignal.setOnCancelListener {

        }
        return cancelSignal
    }
}