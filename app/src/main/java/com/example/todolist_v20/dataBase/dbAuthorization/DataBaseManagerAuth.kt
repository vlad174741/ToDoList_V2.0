package com.example.todolist_v20.dataBase.dbAuthorization

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.todolist_v20.objects.SharedPreference
import com.example.todolist_v20.objects.Variable

class DataBaseManagerAuth(var context: Context) {

    private var db: SQLiteDatabase? = null
    private val dbAuthTable = DbAuthorizationTable
    private val dbAuthHelper = DataBaseHelperAuth(context)


    fun openDataBase(){

        db = dbAuthHelper.writableDatabase

    }

    fun insertOptionToDB(){

        val selection = "${dbAuthTable.COLUMN_USERNAME} = '${SharedPreference.authUsernamePref}'"

        val values = ContentValues().apply {
            put(dbAuthTable.COLUMN_PREF_THEME, Variable.prefTheme)
            put(dbAuthTable.COLUMN_PREF_FINGER_SCREEN, Variable.prefFingerPrint)
            put(dbAuthTable.COLUMN_PASSWORD, Variable.password)
        }
        db?. update(dbAuthTable.TABLE_NAME,values, selection,null)
    }

    private fun createAccount(username: String) {

        val values = ContentValues().apply {
            put(dbAuthTable.COLUMN_USERNAME, username)
            put(dbAuthTable.COLUMN_PASSWORD, "")
            put(dbAuthTable.COLUMN_PREF_FINGER_SCREEN, Variable.prefFingerPrint)
            put(dbAuthTable.COLUMN_PREF_THEME, Variable.prefTheme)
        }
        db?.insert(dbAuthTable.TABLE_NAME, null, values)
    }

    fun checkAccount() {

        val selection = "${dbAuthTable.COLUMN_USERNAME} = ?"

        val cursor = db?.query(

            dbAuthTable.TABLE_NAME,
            arrayOf(dbAuthTable.COLUMN_USERNAME),
            selection,
            arrayOf(SharedPreference.authUsernamePref),
            null, null, null
        )

        return cursor. use {

            if (cursor != null && cursor.count!=0) {
                findAccountId(SharedPreference.authUsernamePref)
            }else{
                createAccount(SharedPreference.authUsernamePref)
            }

        }

    }



    private fun findAccountId(username: String) {

        val selection = "${dbAuthTable.COLUMN_USERNAME} = ?"


        val cursor = db?.query(dbAuthTable.TABLE_NAME,
            arrayOf(dbAuthTable.COLUMN_ID, dbAuthTable.COLUMN_PASSWORD, dbAuthTable.COLUMN_USERNAME,
                dbAuthTable.COLUMN_PREF_FINGER_SCREEN, dbAuthTable.COLUMN_PREF_THEME),
            selection, arrayOf(username),
            null, null, null
        )

        return cursor. use {

            if (cursor != null && cursor.count!=0) {

                cursor.moveToFirst()

                val prefFingerPrint =
                    cursor.getString(cursor.getColumnIndexOrThrow(dbAuthTable.COLUMN_PREF_FINGER_SCREEN))
                val prefTheme =
                    cursor.getString(cursor.getColumnIndexOrThrow(dbAuthTable.COLUMN_PREF_THEME))
                val password =
                    cursor.getString(cursor.getColumnIndexOrThrow(dbAuthTable.COLUMN_PASSWORD))

                Variable.username = cursor.getString(cursor.getColumnIndex(dbAuthTable.COLUMN_USERNAME))
                Variable.prefFingerPrint = prefFingerPrint.toInt()
                Variable.prefTheme = prefTheme.toInt()
                Variable.password = password

                if (prefFingerPrint == "1") { if(password == ""){ Variable.passwordCheck = true } }
                else { Variable.auth=true }
            }
        }

    }

    fun closeDataBase(){
        dbAuthHelper.close()
    }
}