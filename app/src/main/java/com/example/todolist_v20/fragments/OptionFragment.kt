package com.example.todolist_v20.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.todolist_v20.classes.ChangeTheme
import com.example.todolist_v20.classes.ViewModelMy
import com.example.todolist_v20.dataBase.dbAuthorization.DataBaseManagerAuth
import com.example.todolist_v20.databinding.AuthPinFormBinding
import com.example.todolist_v20.objects.Variable
import com.example.todolist_v20.databinding.FragmentOptionBinding
import com.example.todolist_v20.objects.Variable.dbManagerAuth

@SuppressLint("StaticFieldLeak")
lateinit var bindingOptionFragment: FragmentOptionBinding
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
        dbManagerAuth = DataBaseManagerAuth(activity as AppCompatActivity)



        if (Variable.prefFingerPrint == 0){
            bindingOptionFragment.radioButtonNoOptionAuthPS.isChecked = true
        }else{
            bindingOptionFragment.radioButtonYesOptionAuthPS.isChecked = true
        }

        bindingOptionFragment.radioButtonNoOptionAuthPS.setOnClickListener{
            Variable.prefFingerPrint = 0
            Variable.password = ""
            dbManagerAuth.insertOptionToDB()
        }
        bindingOptionFragment.radioButtonYesOptionAuthPS.setOnClickListener{
            Variable.prefFingerPrint = 1
            dbManagerAuth.insertOptionToDB()

        }



        when (Variable.prefTheme){
            0-> bindingOptionFragment.radioButtonSystemOptionTheme.isChecked = true
            1-> bindingOptionFragment.radioButtonDarkOptionTheme.isChecked = true
            2-> bindingOptionFragment.radioButtonLightOptionTheme.isChecked = true
        }

        bindingOptionFragment.radioButtonSystemOptionTheme.setOnClickListener{
            Variable.prefTheme = 0
            dbManagerAuth.insertOptionToDB()
            ChangeTheme().themeChange(0, (activity as AppCompatActivity).delegate)

        }
        bindingOptionFragment.radioButtonDarkOptionTheme.setOnClickListener{
            Variable.prefTheme = 1
            dbManagerAuth.insertOptionToDB()
            ChangeTheme().themeChange(1, (activity as AppCompatActivity).delegate)

        }
        bindingOptionFragment.radioButtonLightOptionTheme.setOnClickListener{
            Variable.prefTheme = 2
            dbManagerAuth.insertOptionToDB()
            ChangeTheme().themeChange(2, (activity as AppCompatActivity).delegate)
        }




    }

    override fun onResume() {
        super.onResume()
        dbManagerAuth.openDataBase()
        if(rcAdapter.isEnable){ rcAdapter.clearItemSelect()}



    }

    override fun onPause() {
        super.onPause()
        dbManagerAuth.closeDataBase()
    }

    companion object {

        @JvmStatic
        fun newInstance() = OptionFragment()
    }
}