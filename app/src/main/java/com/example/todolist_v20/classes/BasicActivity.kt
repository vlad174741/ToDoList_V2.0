package com.example.todolist_v20.classes

import androidx.appcompat.app.AppCompatActivity

open class BasicActivity : AppCompatActivity() {

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}