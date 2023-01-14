package com.example.todolist_v20.classes

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.example.todolist_v20.dataBase.dbContent.DataBaseManager
import com.example.todolist_v20.dataBase.dbContent.MyIntentConstant
import com.example.todolist_v20.databinding.ActivityEditBinding
import com.example.todolist_v20.objects.PhotoAndImage
import com.example.todolist_v20.objects.Tags
import com.example.todolist_v20.objects.Variable


@SuppressLint("StaticFieldLeak")
lateinit var bindingEdit: ActivityEditBinding
lateinit var tag: String
lateinit var imageName: String
lateinit var uriImageDb: Uri
lateinit var i: Intent
var id = 0




class EditActivity: BasicActivity() {

    private var dataBaseManager = DataBaseManager(this)

    private val getResultCapturePhoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

        if (it.resultCode == Activity.RESULT_OK && it.data == null ) {
            uriImageDb = PhotoAndImage.uri
            bindingEdit.imageViewActivityEdit.setImageURI(uriImageDb)
            checkImage()
            PhotoAndImage.sendMessageGallery(this, PhotoAndImage.filePhoto)
            Variable.imgURI = uriImageDb.toString()
            Log.d("idDir", "save capture photo")
        }else{
            bindingEdit.imageViewActivityEdit.setImageURI(uriImageDb)
            checkImage()
            if (it.data?.data == null){
                    checkDeletePhoto()
                    Log.d("idDir", "delete capture photo")
            }
        }
    }

    private val getResultGalleryPhoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

        if (it.resultCode == Activity.RESULT_OK ) {
            Log.d("idDir", "save image gallery")
            bindingEdit.imageViewActivityEdit.setImageURI(it.data?.data)
            PhotoAndImage.uri = Uri.parse("")
            Variable.imgURI = it.data?.data.toString()
            checkImage()

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingEdit = ActivityEditBinding.inflate(layoutInflater)
        setContentView(bindingEdit.root)
        dataBaseManager.openDataBase()

        i = intent
        id = i.getIntExtra(MyIntentConstant.INTENT_ID_KEY,0)


        Tags.dbTag = i.getStringExtra(MyIntentConstant.INTENT_TAG_KEY).toString()
        tag = Tags.dbTag
        Tags.checkTagEditActivity(tag)


        uriImageDb = i.getStringExtra(MyIntentConstant.INTENT_URL_KEY).toString().toUri()
        Variable.imgURI = uriImageDb.toString()
        PhotoAndImage.uri = Uri.parse("")



        bindingEdit.editTextEditActivityTitle.setText(i.getStringExtra(MyIntentConstant.INTENT_TITLE_KEY))
        bindingEdit.editTextEditActivitySubtitle.setText(i.getStringExtra(MyIntentConstant.INTENT_SUBTITLE_KEY))
        bindingEdit.imageViewActivityEdit.setImageURI(uriImageDb)
        imageName =  bindingEdit.editTextEditActivityTitle.text.toString()

        checkImage()

        bindingEdit.apply {
            //Кнопка для сохранения заметки
            buttonSaveEditActivity.setOnClickListener {
                val title = editTextEditActivityTitle.text.toString()
                val subtitle = editTextEditActivitySubtitle.text.toString()
                checkSavePhoto()
                dataBaseManager.updateToDataBase(title, subtitle, id, saveTag(), Variable.imgURI)
                Variable.imgURI = "empty"
                uriImageDb = Uri.parse("")
                PhotoAndImage.uri = Uri.parse("")
                Tags.dbTag = "empty"
                finish()

            }
            //Кнопка для скрытия окна с изображением
            floatingActionButtonHideImageEditActivity.setOnClickListener {
                cardViewImageActivityEdit.visibility = View.GONE
                floatingActionButtonHideImageEditActivity.visibility = View.GONE
                actionImageButton.visibility = View.GONE
            }
            //Кнопка для выбора действий для изображения
            floatingActionButtonActionImageEditActivity.setOnClickListener {
                if (cardViewImageActivityEdit.visibility == View.GONE){

                    if (imageViewActivityEdit.drawable == null){
                        if (actionImageButton.visibility == View.GONE) {
                            actionImageButton.visibility = View.VISIBLE
                        }else{
                            actionImageButton.visibility = View.GONE
                        }
                    }else {
                        if (actionImageButton.visibility == View.VISIBLE) {
                            actionImageButton.visibility = View.GONE
                        } else {
                            cardViewImageActivityEdit.visibility = View.VISIBLE
                            floatingActionButtonHideImageEditActivity.visibility = View.VISIBLE
                        }
                    }
                }else{
                    if (actionImageButton.visibility == View.GONE) {
                        actionImageButton.visibility = View.VISIBLE
                    }else{
                        actionImageButton.visibility = View.GONE
                    }
                }
            }

            //Кнопка для добавления изображения через галерею
            floatingActionButtonAddImageEditActivity.setOnClickListener {
                PhotoAndImage.chooseImageGallery(getResultGalleryPhoto)
            }
            //Кнопка для добавления изображения через камеру
            floatingActionButtonAddPhotoEditActivity.setOnClickListener {
                checkPermissions()
            }
            //Кнопка для удаления изображения
            floatingActionButtonDeletePhotoEditActivity.setOnClickListener {
                alertDeleteDialog()

            }

            imageViewActivityEdit.setOnClickListener {

            }

            //Кнопки для фильтров

            textViewTagCardEditActivity.setOnClickListener {
                if (scrollTagWindowEditActivity.visibility == View.GONE) {
                    scrollTagWindowEditActivity.visibility = View.VISIBLE
                } else {
                    scrollTagWindowEditActivity.visibility = View.GONE
                }
            }
            radioButtonEditActivityTagHome.setOnClickListener {
                checkTagSelection(radioButtonEditActivityTagHome, Tags.homeTag)
            }
            radioButtonEditActivityTagShop.setOnClickListener {
                checkTagSelection(radioButtonEditActivityTagShop, Tags.shopTag)
            }
            radioButtonEditActivityTagBank.setOnClickListener {
                checkTagSelection(radioButtonEditActivityTagBank, Tags.bankTag)
            }
            radioButtonEditActivityTagWork.setOnClickListener {
                checkTagSelection(radioButtonEditActivityTagWork, Tags.workTag)
            }
            radioButtonEditActivityTagWeekend.setOnClickListener {
                checkTagSelection(radioButtonEditActivityTagWeekend, Tags.weekendTag)
            }
            radioButtonEditActivityTagSport.setOnClickListener {
                checkTagSelection(radioButtonEditActivityTagSport, Tags.sportTag)
            }
            radioButtonEditActivityTagClear.setOnClickListener {
                checkTagSelection(radioButtonEditActivityTagClear, "empty")
            }


        }
    }





    private fun alertDeleteDialog(){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Удалить")
        alertDialog.setMessage("Действительно хотите удалить изоброжение из заметки?")
        alertDialog.setPositiveButton("Удалить"){_,_ ->
            bindingEdit.apply {
                Variable.imgURI = ""
                imageViewActivityEdit.setImageDrawable(null)
                cardViewImageActivityEdit.visibility = View.GONE
                actionImageButton.visibility = View.GONE
                floatingActionButtonHideImageEditActivity.visibility = View.GONE
            }
        }
        alertDialog.setNegativeButton("Нет"){_,_ ->
            bindingEdit.actionImageButton.visibility = View.GONE
        }
        alertDialog.show()
    }

    private fun checkImage() {
        bindingEdit.apply {
            if (imageViewActivityEdit.drawable != null) {
                floatingActionButtonHideImageEditActivity.visibility = View.VISIBLE
                floatingActionButtonDeletePhotoEditActivity.visibility = View.VISIBLE
                cardViewImageActivityEdit.visibility = View.VISIBLE
                actionImageButton.visibility = View.GONE
            }else{
                floatingActionButtonDeletePhotoEditActivity.visibility = View.GONE
                floatingActionButtonHideImageEditActivity.visibility = View.GONE
                cardViewImageActivityEdit.visibility = View.GONE
                actionImageButton.visibility = View.GONE
            }
        }
    }

    private fun checkTagSelection(button: RadioButton, tag: String){
        Tags.tagSelectEdit(button, tag, bindingEdit.textViewTagCardEditActivity)
    }

    private fun checkSavePhoto(){

        if (PhotoAndImage.uri == "".toUri()){
            if(Variable.imgURI == "") {
                Variable.imgURI = "empty"
                PhotoAndImage.uri = Uri.parse("")
            }
        }else{
            Variable.imgURI = PhotoAndImage.uri.toString()
        }

    }

    private fun checkDeletePhoto(){
        if (PhotoAndImage.uri != "".toUri()){
            PhotoAndImage.deletePhoto(this, PhotoAndImage.uri)
            PhotoAndImage.uri = Uri.parse("")
            uriImageDb = Uri.parse("")
            PhotoAndImage.sendMessageGallery(this, PhotoAndImage.filePhoto)
        }else{
            if (uriImageDb != "".toUri()){
            PhotoAndImage.deletePhoto(this, uriImageDb)
                if (PhotoAndImage.uri != "".toUri()) {
                    val f = uriImageDb.toFile()
                    PhotoAndImage.sendMessageGallery(this, f)
                    PhotoAndImage.uri = Uri.parse("")
                }else{
                    Log.d("idDir", "image data base is empty")


                }
            }else{
                Log.d("idDir", "image data base is empty")


            }
        }
    }

    private fun saveTag(): String{
        tag = if(Tags.dbTag != "empty") {
            Tags.dbTag

        } else {

            if (Tags.dbTag == "empty") {
                "empty"
            } else {
                i.getStringExtra(MyIntentConstant.INTENT_TAG_KEY).toString()
            }
        }
        Log.d("idDir", "save tag:$tag")

        return tag
    }


    private fun checkPermissions() {
        if (this.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            } != PackageManager.PERMISSION_GRANTED) {
            Log.d("idDir", "Request Permissions")
            requestMultiplePermissions.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ))
        } else {
            Log.d("idDir", "Permission Already Granted")
            PhotoAndImage.takeFullPhoto(getResultCapturePhoto, imageName)

        }
    }

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                Log.d("idDir", "${it.key} = ${it.value}")
            }
            if (permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true && permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true) {
                Log.d("idDir", "Permission granted")
                PhotoAndImage.takeFullPhoto(getResultCapturePhoto, imageName)

            } else {
                Log.d("idDir", "Permission not granted")
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        dataBaseManager.closeDataBase()

    }
}