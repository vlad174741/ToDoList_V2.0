package com.example.todolist_v20.dataBase.dbContent

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.example.todolist_v20.objects.Variable
import com.example.todolist_v20.dataClass.DataRcView

class DataBaseManager(context: Context) {

    private val dbHelper = DataBaseHelper(context)
    private val dbContentTable = DbContentTable
    private var db: SQLiteDatabase? = null

    fun openDataBase(){

        db = dbHelper.writableDatabase

    }

    fun insertToDataBase(title: String, subtitle: String, tag: String, img: String){

        val values = ContentValues().apply {

            put(dbContentTable.COLUMN_TITLE, title)
            put(dbContentTable.COLUMN_SUBTITLE, subtitle)
            put(dbContentTable.COLUMN_TAGS, tag)
            put(dbContentTable.COLUMN_IMAGE_URI, img)
            put(dbContentTable.COLUMN_ACCOUNTS, Variable.username)


        }
        db?. insert(dbContentTable.TABLE_NAME,null, values)
    }

    fun updateToDataBase(title: String, subtitle: String, id: Int, tag: String, img: String){

        val selection = BaseColumns._ID + "=$id"
        db?.delete(dbContentTable.TABLE_NAME, selection, null)


        val values = ContentValues().apply {

            put(dbContentTable.COLUMN_TITLE, title)
            put(dbContentTable.COLUMN_SUBTITLE, subtitle)
            put(dbContentTable.COLUMN_TAGS, tag)
            put(dbContentTable.COLUMN_IMAGE_URI, img)
            put(dbContentTable.COLUMN_ACCOUNTS, Variable.username)



        }
        db?. insert(dbContentTable.TABLE_NAME,null, values)
    }



    @SuppressLint("Range")
    fun readDataBase(String: String,selection: String): ArrayList<DataRcView>{

        val dataList = ArrayList<DataRcView>()

        val cursor = db?.query(
            DbContentTable.TABLE_NAME
            , null,selection, arrayOf(String)
            ,null,null,null)


        while(cursor?.moveToNext()!!){

            val  dataTitle = cursor.getString(cursor.getColumnIndex(dbContentTable.COLUMN_TITLE))

            val  dataSubtitle = cursor.getString(cursor.getColumnIndex(dbContentTable.COLUMN_SUBTITLE))

            val  dataTag = cursor.getString(cursor.getColumnIndex(dbContentTable.COLUMN_TAGS))

            val  dataURI = cursor.getString(cursor.getColumnIndex(dbContentTable.COLUMN_IMAGE_URI))

            val dataID = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))


            val dataRC = DataRcView()
            dataRC.title = dataTitle
            dataRC.subtitle = dataSubtitle
            dataRC.tag = dataTag
            dataRC.uri = dataURI
            dataRC.idItem = dataID



            dataList.add(dataRC)

        }
        cursor.close()
        return dataList
    }

    fun closeDataBase(){
        dbHelper.close()
    }

    fun removeItemToDb(id: String) {

        val selection = BaseColumns._ID + "=$id"
        db?.delete(dbContentTable.TABLE_NAME, selection, null)

    }

}