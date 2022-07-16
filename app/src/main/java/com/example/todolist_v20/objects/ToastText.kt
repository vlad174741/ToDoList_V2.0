package com.example.todolist_v20.objects

import android.content.Context
import android.widget.Toast

object ToastText {
    fun longToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    fun shortToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}