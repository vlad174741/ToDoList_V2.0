package com.example.todolist_v20.classes

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.core.net.toUri
import com.example.todolist_v20.dataBase.dbContent.DataBaseManager
import com.example.todolist_v20.dataBase.dbContent.MyIntentConstant
import com.example.todolist_v20.databinding.ActivityEditBinding

@SuppressLint("StaticFieldLeak")
lateinit var bindingEdit: ActivityEditBinding
lateinit var uriImage: Uri



class EditActivity: BasicActivity() {
    var dataBaseManager= DataBaseManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingEdit = ActivityEditBinding.inflate(layoutInflater)
        setContentView(bindingEdit.root)
        var i =intent
        var id = 0
        dataBaseManager.openDataBase()

        uriImage = i.getStringExtra(MyIntentConstant.INTENT_URL_KEY).toString().toUri()
        var tag = i.getStringExtra(MyIntentConstant.INTENT_TAG_KEY).toString()
        Log.d("uriImage", "$tag")

        bindingEdit.textViewEditActivityTitle.setText(i.getStringExtra(MyIntentConstant.INTENT_TITLE_KEY))
        bindingEdit.textViewEditActivitySubtitle.setText(i.getStringExtra(MyIntentConstant.INTENT_SUBTITLE_KEY))
        bindingEdit.imageViewActivityEdit.setImageURI(uriImage)


        id = i.getIntExtra(MyIntentConstant.INTENT_ID_KEY,0)





        bindingEdit.buttonEditActivitySave.setOnClickListener {


            var title = bindingEdit.textViewEditActivityTitle.text.toString()
            var subtitle = bindingEdit.textViewEditActivitySubtitle.text.toString()

            dataBaseManager.updateToDataBase(title,subtitle,id,tag, uriImage.toString())
            finish()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dataBaseManager.closeDataBase()

    }
}