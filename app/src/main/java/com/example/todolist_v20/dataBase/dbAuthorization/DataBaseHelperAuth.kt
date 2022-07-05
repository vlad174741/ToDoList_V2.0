package com.example.todolist_v20.dataBase.dbAuthorization

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val dbAuthTable = DbAuthorizationTable

class DataBaseHelperAuth(context: Context)
    : SQLiteOpenHelper
    (context, dbAuthTable.DATABASE_NAME, null , dbAuthTable.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(dbAuthTable.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(dbAuthTable.DELETE_TABLE)
        onCreate(db)
    }


}