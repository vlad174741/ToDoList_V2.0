package com.example.todolist_v20.dataBase.dbContent

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.text.Selection
import com.example.todolist_v20.Variable
import com.example.todolist_v20.dataClass.DataRcView

class DataBaseManager(context: Context) {

    private val dbHelper = DataBaseHelper(context)
    private val dbContentTable = DbContentTable
    var db: SQLiteDatabase? = null

    fun openDataBase(){

        db = dbHelper.writableDatabase

    }

    fun insertToDataBase(title: String, subtitle: String, tag: String){

        val values = ContentValues().apply {

            put(DbContentTable.COLUMN_TITLE, title)
            put(DbContentTable.COLUMN_SUBTITLE, subtitle)
            put(DbContentTable.COLUMN_TAGS, tag)
            put(DbContentTable.COLUMN_ACCOUNTS, Variable.email)


        }
        db?. insert(DbContentTable.TABLE_NAME,null, values)
    }

    fun updateToDataBase(title: String, subtitle: String, id: Int){

        val selection = BaseColumns._ID + "=$id"
        db?.delete(DbContentTable.TABLE_NAME, selection, null)


        val values = ContentValues().apply {

            put(DbContentTable.COLUMN_TITLE, title)
            put(DbContentTable.COLUMN_SUBTITLE, subtitle)
            put(DbContentTable.COLUMN_ACCOUNTS, Variable.email)



        }
        db?. insert(DbContentTable.TABLE_NAME,null, values)
    }



    @SuppressLint("Range")
    fun readDataBase(String: String,selection: String): ArrayList<DataRcView>{

        val dataList = ArrayList<DataRcView>()

        val cursor = db?.query(
            DbContentTable.TABLE_NAME
            , null,selection, arrayOf(String)
            ,null,null,null)


            while(cursor?.moveToNext()!!){

                val  dataTitle = cursor.getString(cursor.getColumnIndex(DbContentTable.COLUMN_TITLE))

                val  dataSubtitle = cursor.getString(cursor.getColumnIndex(DbContentTable.COLUMN_SUBTITLE))

                val dataID = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))


                val dataRC = DataRcView()
                dataRC.title = dataTitle
                dataRC.subtitle = dataSubtitle
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
        db?.delete(DbContentTable.TABLE_NAME, selection, null)

    }

}