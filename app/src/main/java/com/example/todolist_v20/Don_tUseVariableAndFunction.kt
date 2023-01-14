package com.example.todolist_v20

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileNotFoundException

object Don_tUseVariableAndFunction {


    //Выбор приложения для отправки сообщения
//    private fun sendEmail(){
//        val intent = Intent(Intent.ACTION_SEND)
//        intent.type = "text/plain"
//        intent.putExtra(Intent.EXTRA_EMAIL, "vlad79917991@gmail.com")
//        intent.putExtra(Intent.EXTRA_SUBJECT, "4545")
//        intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.")
//        startActivity(Intent.createChooser(intent, "Send Email"))
//    }


    //Создание файла низкокачественного растрового изображения чере imageView и его drawable

//            val d: Drawable = bindingEdit.imageViewActivityEdit.drawable
//            val bitmap = (d as BitmapDrawable).bitmap
//            MediaStore.Images.Media.insertImage(contentResolver, bitmap, "name", ".jpg")


    //Создание файла растрового изображения высокого качества чере imageView и его drawable

//    fun saveCameraImage(bitmap: Bitmap)
//    {
//        try {
//            val collection =  MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//
//            val dirDest = File(
//                Environment.DIRECTORY_PICTURES,
//                this.getString(R.string.app_name)
//            )
//            var IMAGE_EXTENSIONS = "jpg"
//            val date = System.currentTimeMillis()
//            val fileName = "$date.${IMAGE_EXTENSIONS}"
//
//
//            val contentValues = ContentValues().apply {
//                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
//                put(MediaStore.MediaColumns.MIME_TYPE, "image/$IMAGE_EXTENSIONS")
//                put(MediaStore.MediaColumns.DATE_ADDED, date)
//                put(MediaStore.MediaColumns.DATE_MODIFIED, date)
//                put(MediaStore.MediaColumns.SIZE, bitmap.byteCount)
//                put(MediaStore.MediaColumns.WIDTH, bitmap.width)
//                put(MediaStore.MediaColumns.HEIGHT, bitmap.height)
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//
//                    put(MediaStore.MediaColumns.RELATIVE_PATH, "$dirDest${File.separator}")
//                    put(MediaStore.Images.Media.IS_PENDING, 1)
//                }
//            }
//
//            val imageUri = this.contentResolver.insert(collection, contentValues)
//
//
//
//            imageUri?.let { uri ->
//                this@EditActivity.contentResolver.openOutputStream(uri, "w").use { out ->
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
//                }
//
//                contentValues.clear()
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)}
//                this@EditActivity.contentResolver.update(uri, contentValues, null, null)
//            }
//
//
//
//        } catch (e: FileNotFoundException) {
//
//        }
//
//    }

}