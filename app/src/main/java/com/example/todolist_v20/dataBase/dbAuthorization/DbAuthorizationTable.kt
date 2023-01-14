package com.example.todolist_v20.dataBase.dbAuthorization


object DbAuthorizationTable {

    const val TABLE_NAME = "authorization_table"
    const val COLUMN_USERNAME = "username"
    const val COLUMN_PASSWORD = "password"
    const val COLUMN_ID = "id"
    const val COLUMN_PREF_FINGER_SCREEN = "prefFingerPrint"
    const val COLUMN_PREF_THEME = "prefTheme"


    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "DataBaseAuthorization"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "$COLUMN_ID INTEGER PRIMARY KEY," +
            "$COLUMN_USERNAME TEXT UNIQUE COLLATE NOCASE NOT NULL," +
            "$COLUMN_PASSWORD TEXT NOT NULL," +
            "$COLUMN_PREF_FINGER_SCREEN INTEGER," +
            "$COLUMN_PREF_THEME INTEGER)"


    const val DELETE_TABLE = "DROP TABLE IF NOT EXISTS $TABLE_NAME"
}