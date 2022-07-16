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


    fun getPhotoFile(fileName: String, context: Context): File {
        val directoryStorage = ContextWrapper(context)
            .getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directoryStorage)
    }



     fun takeFullPhoto(uriPhoto: Uri, getResult: ActivityResultLauncher<Intent>) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPhoto)
        getResult.launch(intent)
    }

     fun chooseImageGallery(getResult: ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        getResult.launch(intent)
    }
}

