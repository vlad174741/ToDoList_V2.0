package com.example.todolist_v20.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.inputmethodservice.KeyboardView
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginBottom
import androidx.fragment.app.activityViewModels
import com.example.todolist_v20.*
import com.example.todolist_v20.dataBase.dbContent.DataBaseManager
import com.example.todolist_v20.dataClass.Data
import com.example.todolist_v20.databinding.FragmentEditBinding

@SuppressLint("StaticFieldLeak")
lateinit var bindingEditFragment: FragmentEditBinding

class EditFragment : Fragment() {
    private val model: ViewModelMy by activityViewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {



        bindingEditFragment = FragmentEditBinding.inflate(inflater, container, false)

        return bindingEditFragment.root



    }



    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dbManager = DataBaseManager(activity as AppCompatActivity)
        dbManager.openDataBase()

        val hideKeyboard = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager





        bindingEditFragment.buttonEditFragment.setOnClickListener{

            val title = bindingEditFragment.editTextEditFragmentTitle.text
            val subtitle = bindingEditFragment.editTextEditFragmentSubtitle.text

            if (title.isEmpty()){
                Toast.makeText(activity as AppCompatActivity, "Введите заголовок", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(activity as AppCompatActivity, "Сохраняем", Toast.LENGTH_SHORT).show()
                hideKeyboard.hideSoftInputFromWindow(view.windowToken, 0)
                Variable.tag = title.toString()

                dbManager.insertToDataBase(title.toString(),subtitle.toString())
                Variable.tag = ""
                title.clear()
                subtitle.clear()



            }
            Log.d("id", Variable.tag)



            model.plant.value = Data("Info22", "UseCase22")
        }



    }



    companion object {



        @JvmStatic
        fun newInstance() = EditFragment()
    }
}