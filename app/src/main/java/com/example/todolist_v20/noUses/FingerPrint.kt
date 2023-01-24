package com.example.todolist_v20.noUses

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.hardware.biometrics.BiometricPrompt
import android.os.CancellationSignal
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.todolist_v20.classes.AuthClass
import com.example.todolist_v20.classes.MainActivity
import com.example.todolist_v20.objects.Variable

object FingerPrint {

    fun fingerPrintDialog(context: Context) {

        val mainExecutor = ContextWrapper(context)


        val prompt = BiometricPrompt.Builder(context)
            .setTitle("Авторизация по отпечатку пальца")
            .setDescription("Используйте свой отпечаток для авторизации")
            .setNegativeButton("Отмена", mainExecutor.mainExecutor
            ) { _, _ -> }
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

                    if (errorCode != BiometricPrompt.BIOMETRIC_ERROR_NO_BIOMETRICS  &&
                        errorCode != BiometricPrompt.BIOMETRIC_ERROR_HW_UNAVAILABLE &&
                        errorCode != BiometricPrompt.BIOMETRIC_ERROR_HW_NOT_PRESENT &&
                        errorCode != BiometricPrompt.BIOMETRIC_ERROR_CANCELED &&
                        errorCode != BiometricPrompt.BIOMETRIC_ERROR_NO_DEVICE_CREDENTIAL
                    ) {
                        Variable.fingerPrintYes = true; AuthClass().onResume()}
                    else{
                        Variable.fingerPrintYes = false}







                }
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    val main = Intent(context, MainActivity::class.java)
                    ContextCompat.startActivity(context,main, null)
                    Variable.auth =true
                }
            }
        )


    }

    private fun getCancellationSignal(): CancellationSignal {




        val cancelSignal = CancellationSignal()
        cancelSignal.setOnCancelListener {

        }
        return cancelSignal
    }
}