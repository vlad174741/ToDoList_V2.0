package com.example.todolist_v20.dataBase.dbAuthorization

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.example.todolist_v20.objects.Variable

class DataBaseManagerAuth(var context: Context) {

    private var db: SQLiteDatabase? = null
    private val dbAuthTable = DbAuthorizationTable
    private val dbAuthHelper = DataBaseHelperAuth(context)


    fun openDataBase(){

        db = dbAuthHelper.writableDatabase

    }

    private fun createAccount(username: String, email: String, password: String) {

        val values = ContentValues().apply {


            put(dbAuthTable.COLUMN_USERNAME, username)
            put(dbAuthTable.COLUMN_EMAIL, email)
            put(dbAuthTable.COLUMN_PASSWORD, password)


        }
        if (password==""){
            Toast.makeText(context, "Введите пароль", Toast.LENGTH_SHORT).show()
        }else {

            db?.insert(dbAuthTable.TABLE_NAME, null, values)
        }

    }

    private fun checkAccountsEmailForRegistration(username: String, email: String, password: String) {

           val selection = "${dbAuthTable.COLUMN_EMAIL} = ?"

        val cursor = db?.query(

            dbAuthTable.TABLE_NAME,
            arrayOf(dbAuthTable.COLUMN_EMAIL),
            selection,
            arrayOf(email),
            null, null, null
        )

        return cursor. use {


            if (cursor != null && cursor.count!=0) {
                Toast.makeText(context, "Такой email уже зарегестрирован", Toast.LENGTH_SHORT).show()



            }else{
                Toast.makeText(context, "Пользователь зарегестрирован", Toast.LENGTH_SHORT).show()
                createAccount(username, email, password)


            }
        }

    }

    fun checkAccountsForRegistration(username: String,email: String, password: String) {

        val selection = "${dbAuthTable.COLUMN_USERNAME} = ?"

        val cursor = db?.query(

            dbAuthTable.TABLE_NAME,
            arrayOf(dbAuthTable.COLUMN_USERNAME),
            selection,
            arrayOf(username),
            null, null, null
        )

        return cursor. use {

            if (cursor != null && cursor.count!=0) {
                Toast.makeText(context, "Такой пользователь уже есть", Toast.LENGTH_SHORT).show()
            }
            else{
                checkAccountsEmailForRegistration(username,email,password)
            }
        }

    }


    fun findAccountId(username: String,email: String, password: String) {

        val selection: String = if (email.isNotEmpty()){
            "${dbAuthTable.COLUMN_EMAIL} = ?"
        } else{
            "${dbAuthTable.COLUMN_USERNAME} = ?"
        }

        val cursor = db?.query(

            dbAuthTable.TABLE_NAME,
            arrayOf(dbAuthTable.COLUMN_ID, dbAuthTable.COLUMN_PASSWORD,
                dbAuthTable.COLUMN_USERNAME, dbAuthTable.COLUMN_EMAIL),
           selection,
            if (email.isNotEmpty()){arrayOf(email)}else{arrayOf(username)},
            null, null, null
        )

        return cursor. use {


            if (cursor != null && cursor.count!=0) {

                cursor.moveToFirst()

                val passwordFromDb =
                    cursor.getString(cursor.getColumnIndexOrThrow(dbAuthTable.COLUMN_PASSWORD))


                if (email == "" && username == "") {
                    Toast.makeText(context, "Введите email или логин", Toast.LENGTH_SHORT).show()

                } else {

                    if (passwordFromDb != password) {
                        Toast.makeText(context, "Пароль не верный", Toast.LENGTH_SHORT).show()
                    } else {

                        Toast.makeText(context, "Пароль верный", Toast.LENGTH_SHORT).show()

                        Variable.id= cursor.getInt(cursor.getColumnIndex(dbAuthTable.COLUMN_ID))
                        Variable.email= cursor.getString(cursor.getColumnIndex(dbAuthTable.COLUMN_EMAIL))

                        if(cursor.getString(cursor.getColumnIndex(dbAuthTable.COLUMN_EMAIL)).isEmpty()){
                            Variable.email = cursor.getString(cursor.getColumnIndex(dbAuthTable.COLUMN_USERNAME))
                        }

                        Variable.username= cursor.getString(cursor.getColumnIndex(dbAuthTable.COLUMN_USERNAME))
                        Variable.auth = true


                    }

                }

            }else {
                if (email == "" && username == "") {
                    Toast.makeText(context, "Введите email или логин", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Нет такого пользователя", Toast.LENGTH_SHORT).show()

                }
            }
        }

    }

    fun closeDataBase(){
        dbAuthHelper.close()
    }
}