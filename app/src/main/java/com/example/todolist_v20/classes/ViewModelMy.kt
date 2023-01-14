package com.example.todolist_v20.classes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist_v20.dataClass.Data

open class ViewModelMy: ViewModel() {


    val plant = MutableLiveData<Data>()
}