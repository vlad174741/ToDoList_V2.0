package com.example.todolist_v20.objects

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import java.io.File
object PhotoAndImage {

    const val FILE_NAME = "photo"
    lateinit var filePhoto: File
    var uri: Uri = Uri.parse("")


    fun deletePhoto(context: Context, uri: Uri){
        val fileToDelete = uri.path?.let { File(it) }
        if (fileToDelete != null) {
            if (fileToDelete.exists()) {
                if (fileToDelete.delete()) {
                    if (fileToDelete.exists()) {
                        fileToDelete.canonicalFile.delete()
                        if (fileToDelete.exists()) {
                            ContextWrapper(context).applicationContext.deleteFile(fileToDelete.name)
                        }
                    }
                    Log.e("", "File Deleted " + uri.path)
                } else {
                    Log.e("", "File not Deleted " + uri.path)
                }
            }
        }
    }


     private fun getPhotoFile(fileName: String): File {

        val directoryStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directoryStorage)
    }



     fun takeFullPhoto( getResult: ActivityResultLauncher<Intent>, fileName: String) {
         filePhoto = getPhotoFile(fileName)
         uri = Uri.fromFile(filePhoto)
         val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
         val builder = StrictMode.VmPolicy.Builder()
         StrictMode.setVmPolicy(builder.build())
         intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

         getResult.launch(intent)
    }



     fun chooseImageGallery(getResult: ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
         getResult.launch(intent)
         Log.d("idDir", "take photo")

     }



    fun sendMessageGallery(context: Context, file: File){
        val intentGallery = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).apply {

            data = Uri.fromFile(file)
            Log.d("idDir", "$filePhoto.toString()")

        }
        context.sendBroadcast(intentGallery)
    }

}

