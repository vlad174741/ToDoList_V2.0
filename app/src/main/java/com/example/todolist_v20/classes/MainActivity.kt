package com.example.todolist_v20.classes

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.WindowManager
import com.example.todolist_v20.R
import com.example.todolist_v20.objects.Variable
import com.example.todolist_v20.dataBase.dbContent.DataBaseManager
import com.example.todolist_v20.databinding.ActivityMainBinding
import com.example.todolist_v20.fragments.*
import com.example.todolist_v20.objects.Variable.dbManager

@SuppressLint("StaticFieldLeak")
lateinit var bindingMain: ActivityMainBinding // ViewBinding //

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbManager = DataBaseManager(this)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)  // ViewBinding //

        setContentView(bindingMain.root)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        Log.d("liveActivity", "MainActivity.onCreate")


        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                .replace(R.id.constrain_layout_main_activity, ContentTabFragment.newInstance())
                .commit()
        }

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.d("liveActivity", "MainActivity.saveInstanceState")

    }

    override fun onStart() {
        super.onStart()
        Log.d("liveActivity", "MainActivity.onStart")

    }

   override fun onResume() {
        super.onResume()
        Log.d("liveActivity", "MainActivity.onResume")
   }

    override fun onPause() {
        super.onPause()
        Log.d("liveActivity", "MainActivity.onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.d("liveActivity", "MainActivity.onStop")

    }


    override fun onDestroy() {
        super.onDestroy()
        Variable.auth = false
        Variable.passwordCheck = false
        Variable.fingerPrintYes = false
        dbManager.closeDataBase()
        Log.d("liveActivity", "MainActivity.onDestroy")

    }

}