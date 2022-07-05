package com.example.todolist_v20.dataBase.dbContent

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val dbContentTable = DbContentTable

class DataBaseHelper(context: Context)
    : SQLiteOpenHelper(context, dbContentTable.DATABASE_NAME
    , null, dbContentTable.DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(dbContentTable.CREATE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(dbContentTable.DELETE_TABLE)
        onCreate(db)

    }
}