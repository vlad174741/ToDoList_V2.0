package com.example.todolist_v20.classes

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.todolist_v20.R
import com.example.todolist_v20.objects.Variable
import com.example.todolist_v20.dataBase.dbContent.DataBaseManager
import com.example.todolist_v20.dataBase.dbAuthorization.DataBaseManagerAuth
import com.example.todolist_v20.databinding.ActivityMainBinding
import com.example.todolist_v20.fragments.*

@SuppressLint("StaticFieldLeak")
lateinit var bindingMain: ActivityMainBinding // ViewBinding //

class MainActivity : AppCompatActivity() {
    private val dbManager = DataBaseManager(this)
    private val dbManagerAuth = DataBaseManagerAuth(this)




    private val modelView: ViewModelMy by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain =
            ActivityMainBinding.inflate(layoutInflater)                                                  // ViewBinding //
        setContentView(bindingMain.root)








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
        Log.d("idItemSelect", "${Variable.email}")
        Log.d("idItemSelect", "${Variable.username}")


    }


    override fun onDestroy() {
        super.onDestroy()

        dbManager.closeDataBase()
    }

}