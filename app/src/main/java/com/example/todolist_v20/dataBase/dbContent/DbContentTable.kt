package com.example.todolist_v20.dataBase.dbContent

import android.provider.BaseColumns

object DbContentTable {

    const val TABLE_NAME = "content_table"
    const val COLUMN_TITLE = "title"
    const val COLUMN_SUBTITLE = "subtitle"
    const val COLUMN_TAGS = "tag"
    const val COLUMN_IMAGE_URI = "image_uri"
    const val COLUMN_ACCOUNTS = "account"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "DataBaseContent"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$COLUMN_TITLE TEXT," +
            "$COLUMN_SUBTITLE TEXT," +
            "$COLUMN_TAGS TEXT," +
            "$COLUMN_IMAGE_URI TEXT," +
            "$COLUMN_ACCOUNTS TEXT)"

    const val DELETE_TABLE = "DROP TABLE IF NOT EXISTS $TABLE_NAME"

}