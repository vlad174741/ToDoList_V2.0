package com.example.todolist_v20.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.todolist_v20.classes.AuthClass
import com.example.todolist_v20.classes.ChangeTheme
import com.example.todolist_v20.classes.MainActivity
import com.example.todolist_v20.classes.ViewModelMy
import com.example.todolist_v20.dataBase.dbAuthorization.DataBaseManagerAuth
import com.example.todolist_v20.databinding.AuthPinFormBinding
import com.example.todolist_v20.objects.FingerPrint
import com.example.todolist_v20.objects.Variable
import com.example.todolist_v20.databinding.FragmentOptionBinding

@SuppressLint("StaticFieldLeak")
lateinit var bindingOptionFragment: FragmentOptionBinding
lateinit var dbManagerAuthOptionFragment: DataBaseManagerAuth
lateinit var bindingPin: AuthPinFormBinding




class OptionFragment : Fragment() {
    private val model: ViewModelMy by activityViewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        bindingOptionFragment = FragmentOptionBinding.inflate(inflater,container,false)
        bindingPin = AuthPinFormBinding.inflate(inflater,container,false)


        return bindingOptionFragment.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.plant.observe(activity as LifecycleOwner){}
        dbManagerAuthOptionFragment = DataBaseManagerAuth(activity as AppCompatActivity)



        if (Variable.prefFingerPrint == 0){
            bindingOptionFragment.radioButtonNoOptionAuthPS.isChecked = true
        }else{
            bindingOptionFragment.radioButtonYesOptionAuthPS.isChecked = true
        }

        bindingOptionFragment.radioButtonNoOptionAuthPS.setOnClickListener{
            Variable.prefFingerPrint = 0
            Variable.password = ""
            dbManagerAuthOptionFragment.insertOptionToDB()
        }
        bindingOptionFragment.radioButtonYesOptionAuthPS.setOnClickListener{
            Variable.prefFingerPrint = 1
            dbManagerAuthOptionFragment.insertOptionToDB()

        }



        when (Variable.prefTheme){
            0-> bindingOptionFragment.radioButtonSystemOptionTheme.isChecked = true
            1-> bindingOptionFragment.radioButtonDarkOptionTheme.isChecked = true
            2-> bindingOptionFragment.radioButtonLightOptionTheme.isChecked = true
        }

        bindingOptionFragment.radioButtonSystemOptionTheme.setOnClickListener{
            Variable.prefTheme = 0
            dbManagerAuthOptionFragment.insertOptionToDB()
            ChangeTheme().themeChange(0, (activity as AppCompatActivity).delegate)

        }
        bindingOptionFragment.radioButtonDarkOptionTheme.setOnClickListener{
            Variable.prefTheme = 1
            dbManagerAuthOptionFragment.insertOptionToDB()
            ChangeTheme().themeChange(1, (activity as AppCompatActivity).delegate)

        }
        bindingOptionFragment.radioButtonLightOptionTheme.setOnClickListener{
            Variable.prefTheme = 2
            dbManagerAuthOptionFragment.insertOptionToDB()
            ChangeTheme().themeChange(2, (activity as AppCompatActivity).delegate)
        }




    }

    override fun onResume() {
        super.onResume()
        dbManagerAuthOptionFragment.openDataBase()


    }

    override fun onPause() {
        super.onPause()
        dbManagerAuthOptionFragment.closeDataBase()
    }

    companion object {

        @JvmStatic
        fun newInstance() = OptionFragment()
    }
}