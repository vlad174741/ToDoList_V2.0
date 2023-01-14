package com.example.todolist_v20.objects

import android.util.Log
import android.widget.RadioButton
import android.widget.TextView
import com.example.todolist_v20.classes.EditActivity
import com.example.todolist_v20.classes.bindingEdit
import com.example.todolist_v20.dataBase.dbContent.VariableDbContent
import com.example.todolist_v20.fragments.bindingMainFragment
import com.example.todolist_v20.fragments.dbManager
import com.example.todolist_v20.fragments.rcAdapter

object Tags {

    //Теги
    private lateinit var tagButtonActive: RadioButton
    private var tagUsingMainFragment = ""
    var mainFragmentTag = ""


    var dbTag = "empty"
    var shopTag = "shop"
    var homeTag = "home"
    var workTag = "work"
    var weekendTag = "weekend"
    var bankTag = "bank"
    var sportTag = "sport"



     fun tagSelectEdit(button: RadioButton, tag: String, textView: TextView){
        if (button.isChecked) {
            if (tag == "empty"){
                dbTag = tag
                textView.text = "Нет фильтра"

            }else {
                dbTag = tag + Variable.username
                textView.text = button.text
            }
        }
    }

    fun tagSelectMainFragment(button: RadioButton, tag: String){

        if (button.isChecked) {

            if (tagUsingMainFragment==tag){
                bindingMainFragment.radioGroupTegEditFragment.clearCheck()
                tagUsingMainFragment = ""
                val dataList = dbManager.readDataBase(Variable.username, VariableDbContent.selectionColumnAccount)
                mainFragmentTag = ""
                rcAdapter.updateAdapter(dataList)


            }else {
                tagButtonActive = button
                val result = tag + Variable.username
                val dataList = dbManager.readDataBase(result, VariableDbContent.selectionColumnTag)
                rcAdapter.updateAdapter(dataList)
                tagUsingMainFragment = tag
                mainFragmentTag=result

                Log.d("tag", result)
            }
        }
    }


     fun checkTagEditActivity(tag: String){
        when (tag){
            "empty"->{
                bindingEdit.textViewTagCardEditActivity.text = "Нет фильтра"}
            "home${Variable.username}"->{ bindingEdit.apply {
                radioButtonEditActivityTagHome.isChecked = true
                textViewTagCardEditActivity.text = radioButtonEditActivityTagHome.text}}
            "shop${Variable.username}"->{ bindingEdit.apply {
                radioButtonEditActivityTagShop.isChecked = true
                textViewTagCardEditActivity.text = radioButtonEditActivityTagShop.text}}
            "bank${Variable.username}"->{ bindingEdit.apply {
                radioButtonEditActivityTagBank.isChecked = true
                textViewTagCardEditActivity.text = radioButtonEditActivityTagBank.text}}
            "work${Variable.username}"->{ bindingEdit.apply {
                radioButtonEditActivityTagWork.isChecked = true
                textViewTagCardEditActivity.text = radioButtonEditActivityTagWork.text}}
            "weekend${Variable.username}"->{ bindingEdit.apply {
                radioButtonEditActivityTagWeekend.isChecked = true
                textViewTagCardEditActivity.text = radioButtonEditActivityTagWeekend.text}}
            "sport${Variable.username}"->{ bindingEdit.apply {
                radioButtonEditActivityTagSport.isChecked = true
                textViewTagCardEditActivity.text = radioButtonEditActivityTagSport.text}}
        }
    }



}