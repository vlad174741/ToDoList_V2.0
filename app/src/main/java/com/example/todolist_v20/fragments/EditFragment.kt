package com.example.todolist_v20.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dbManager = DataBaseManager(activity as AppCompatActivity)
        dbManager.openDataBase()
        val hideKeyboard = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        bindingEditFragment.textViewTagCardEditFragment.setOnClickListener {

            bindingEditFragment.radioGroupTegEditFragment.clearCheck()
            bindingEditFragment.textViewTagCardEditFragment.text = "Нет фильтра"
            Variable.dbTag = "empty"

            if (bindingEditFragment.scrollTagWindowEditFragment.visibility == View.GONE) {
                bindingEditFragment.scrollTagWindowEditFragment.visibility = View.VISIBLE
            }else{
                bindingEditFragment.scrollTagWindowEditFragment.visibility = View.GONE
            }


        }

        bindingEditFragment.apply {

            radioButtonEdritFragmentTagHome.setOnClickListener {
                tagCheck(radioButtonEdritFragmentTagHome,Variable.homeTag)
            }
            radioButtonEdritFragmentTagShop.setOnClickListener {
                tagCheck(radioButtonEdritFragmentTagShop,Variable.shopTag)
            }
            radioButtonEdritFragmentTagBank.setOnClickListener {
                tagCheck(radioButtonEdritFragmentTagBank,Variable.bankTag)
            }
            radioButtonEdritFragmentTagWork.setOnClickListener {
                tagCheck(radioButtonEdritFragmentTagWork,Variable.workTag)
            }
            radioButtonEdritFragmentTagWeekend.setOnClickListener {
                tagCheck(radioButtonEdritFragmentTagWeekend,Variable.weekendTag)
            }
            radioButtonEdritFragmentTagSport.setOnClickListener {
                tagCheck(radioButtonEdritFragmentTagSport,Variable.sportTag)
            }
        }

        bindingEditFragment.editTextEditFragmentSubtitle.setOnClickListener {
            hideKeyboard.hideSoftInputFromWindow(view.windowToken, 0)
        }

        bindingEditFragment.buttonSaveEditFragment.setOnClickListener{

            val title = bindingEditFragment.editTextEditFragmentTitle.text
            val subtitle = bindingEditFragment.editTextEditFragmentSubtitle.text

            if (title.isEmpty()){
                Toast.makeText(activity as AppCompatActivity, "Введите заголовок", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(activity as AppCompatActivity, "Сохраняем", Toast.LENGTH_SHORT).show()
                hideKeyboard.hideSoftInputFromWindow(view.windowToken, 0)
                Variable.tag = title.toString()

                dbManager.insertToDataBase(title.toString(),subtitle.toString(),Variable.dbTag)
                Variable.dbTag = "empty"
                bindingEditFragment.textViewTagCardEditFragment.text = "Нет фильтра"
                bindingEditFragment.scrollTagWindowEditFragment.visibility = View.GONE
                title.clear()
                subtitle.clear()
                bindingEditFragment.radioGroupTegEditFragment.clearCheck()



            }





            Log.d("id", Variable.tag)



            model.plant.value = Data("Info22", "UseCase22")
        }



    }

    fun tagCheck(button: RadioButton, tag: String){
        if (button.isChecked) {
            Variable.dbTag = tag+Variable.username
            bindingEditFragment.textViewTagCardEditFragment.text = button.text
        }
    }



    companion object {



        @JvmStatic
        fun newInstance() = EditFragment()
    }
}