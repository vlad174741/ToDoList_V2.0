package com.example.todolist_v20.classes

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.example.todolist_v20.R
import com.example.todolist_v20.dataBase.dbAuthorization.DataBaseManagerAuth
import com.example.todolist_v20.objects.Variable
import com.example.todolist_v20.dataBase.dbContent.DataBaseManager
import com.example.todolist_v20.databinding.ActivityMainBinding
import com.example.todolist_v20.databinding.AuthPinFormBinding
import com.example.todolist_v20.fragments.*
import com.example.todolist_v20.objects.SharedPreference

@SuppressLint("StaticFieldLeak")
lateinit var bindingMain: ActivityMainBinding // ViewBinding //

class MainActivity : AppCompatActivity() {
    private val dbManager = DataBaseManager(this)





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingMain = ActivityMainBinding.inflate(layoutInflater)                                                  // ViewBinding //

        setContentView(bindingMain.root)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)










        if (savedInstanceState == null) {
            Log.d("idItemSelect", "${Variable.auth}")

            supportFragmentManager.beginTransaction()
                .replace(R.id.constrain_layout_main_activity, ContentTabFragment.newInstance())
                .commit()


        }

        bindingMain.apply {


        }
    }

   override fun onResume() {
        super.onResume()
        Log.d("idItemSelect", "${Variable.auth}")
        Log.d("idItemSelect", "${Variable.id}")
        Log.d("idItemSelect", Variable.username)



    }




    override fun onDestroy() {
        super.onDestroy()
        Variable.auth = false

        dbManager.closeDataBase()
    }

}