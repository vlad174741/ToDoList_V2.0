package com.example.todolist_v20

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.todolist_v20.dataBase.dbContent.DataBaseManager
import com.example.todolist_v20.dataBase.dbContent.MyIntentConstant
import com.example.todolist_v20.databinding.ActivityEditBinding

@SuppressLint("StaticFieldLeak")
lateinit var bindingEdit: ActivityEditBinding


class EditActivity: BasicActivity() {
    var dataBaseManager= DataBaseManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingEdit = ActivityEditBinding.inflate(layoutInflater)
        setContentView(bindingEdit.root)
        var i =intent
        var id = 0
        dataBaseManager.openDataBase()

        bindingEdit.textViewEditActivityTitle.setText(i.getStringExtra(MyIntentConstant.INTENT_TITLE_KEY))
        bindingEdit.textViewEditActivitySubtitle.setText(i.getStringExtra(MyIntentConstant.INTENT_SUBTITLE_KEY))

        id = i.getIntExtra(MyIntentConstant.INTENT_ID_KEY,0)





        bindingEdit.buttonEditActivitySave.setOnClickListener {


            var title = bindingEdit.textViewEditActivityTitle.text.toString()
            var subtitle = bindingEdit.textViewEditActivitySubtitle.text.toString()

            dataBaseManager.updateToDataBase(title,subtitle,id)
            finish()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dataBaseManager.closeDataBase()

    }
}