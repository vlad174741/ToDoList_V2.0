package com.example.todolist_v20.objects

import com.example.todolist_v20.dataBase.dbAuthorization.DataBaseManagerAuth
import com.example.todolist_v20.dataBase.dbContent.DataBaseManager


object Variable {

    lateinit var dbManager: DataBaseManager
    lateinit var dbManagerAuth: DataBaseManagerAuth


    var check = 0
    var auth = false
    var username = "user"
    var imgURI = ""
    var id = 0
    var prevPositionRcView = -1
    var passwordCheck = false
    var password = ""
    var fingerPrintYes = false



    //Option
    var prefFingerPrint = 0
    var prefTheme = 0

}
