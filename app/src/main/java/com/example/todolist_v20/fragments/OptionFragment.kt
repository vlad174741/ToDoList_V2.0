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
import com.example.todolist_v20.classes.ViewModelMy
import com.example.todolist_v20.objects.FingerPrint
import com.example.todolist_v20.objects.Variable
import com.example.todolist_v20.databinding.FragmentOptionBinding

@SuppressLint("StaticFieldLeak")
lateinit var bindingOptionFragment: FragmentOptionBinding


class OptionFragment : Fragment() {
    private val model: ViewModelMy by activityViewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        bindingOptionFragment = FragmentOptionBinding.inflate(inflater,container,false)
        return bindingOptionFragment.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.plant.observe(activity as LifecycleOwner){}

        bindingOptionFragment.buttonOptionFragmentLogout.setOnClickListener {

            activity!!.finish()
            Variable.auth = false
            val intentAuth = Intent(activity as AppCompatActivity, AuthClass::class.java)
            startActivity(intentAuth)
        }

        bindingOptionFragment.buttonLoginFingerprint.setOnClickListener {

            FingerPrint.fingerPrintDialog(activity as AppCompatActivity)

        }




    }



    companion object {

        @JvmStatic
        fun newInstance() = OptionFragment()
    }
}