package com.example.todolist_v20.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist_v20.objects.Variable
import com.example.todolist_v20.classes.ViewModelMy
import com.example.todolist_v20.adapters.RecyclerViewAdapter
import com.example.todolist_v20.dataBase.dbContent.DataBaseManager
import com.example.todolist_v20.dataBase.dbContent.VariableDbContent
import com.example.todolist_v20.databinding.FragmentMainBinding
import com.example.todolist_v20.objects.Tags

@SuppressLint("StaticFieldLeak")
lateinit var bindingMainFragment: FragmentMainBinding
@SuppressLint("StaticFieldLeak")
lateinit var rcAdapter: RecyclerViewAdapter

@SuppressLint("StaticFieldLeak")
lateinit var dbManager: DataBaseManager



class MainFragment : Fragment() {
    private val model: ViewModelMy by activityViewModels()






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {



        bindingMainFragment = FragmentMainBinding.inflate(inflater, container, false)
        return bindingMainFragment.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcAdapter = RecyclerViewAdapter(ArrayList(), activity as AppCompatActivity)
        dbManager = DataBaseManager(activity as AppCompatActivity)





        bindingMainFragment.recyclerViewMainFragment.adapter = rcAdapter
        bindingMainFragment.recyclerViewMainFragment
            .layoutManager = LinearLayoutManager(activity as AppCompatActivity)

        bindingMainFragment.apply {

            radioButtonEdritFragmentTagHome.setOnClickListener {
                tagSelection(radioButtonEdritFragmentTagHome, Tags.homeTag)
            }
            radioButtonEdritFragmentTagShop.setOnClickListener {
                tagSelection(radioButtonEdritFragmentTagShop, Tags.shopTag)
            }
            radioButtonEdritFragmentTagBank.setOnClickListener {
                tagSelection(radioButtonEdritFragmentTagBank, Tags.bankTag)
            }
            radioButtonEdritFragmentTagWork.setOnClickListener {
                tagSelection(radioButtonEdritFragmentTagWork, Tags.workTag)
            }
            radioButtonEdritFragmentTagWeekend.setOnClickListener {
                tagSelection(radioButtonEdritFragmentTagWeekend, Tags.weekendTag)
            }
            radioButtonEdritFragmentTagSport.setOnClickListener {
                tagSelection(radioButtonEdritFragmentTagSport, Tags.sportTag)
            }
        }

        fun rcViewReadDB() {
            val dataList = dbManager.readDataBase(Variable.email,VariableDbContent.selectionColumnAccount)

            rcAdapter.updateAdapter(dataList)
        }

        dbManager.openDataBase()
        rcViewReadDB()





        bindingMainFragment.button.setOnClickListener{
            Log.d("idItemSelect", "${Variable.check}")


            if(rcAdapter.itemSelectList.isEmpty()){
                Toast.makeText(activity as AppCompatActivity, "Выберети элементы для удаления.", Toast.LENGTH_SHORT).show()

            }else{
                rcAdapter.alertDeleteDialog(0)
            }


        }


        model.plant.observe(activity as LifecycleOwner){
            rcViewReadDB()
        }
    }

    private fun tagSelection(button: RadioButton, tag: String){
        Tags.tagSelectMainFragment(button, tag)
    }


    override fun onResume() {
        super.onResume()
        val dataList = dbManager.readDataBase(Variable.email, VariableDbContent.selectionColumnAccount)
        val dataListTag = dbManager.readDataBase(Tags.mainFragmentTag, VariableDbContent.selectionColumnTag)


        if (Variable.check==1) {
            Variable.check = 0
            bindingMainFragment.button.visibility = View.VISIBLE
        }else{
            bindingMainFragment.button.visibility = View.GONE
        }
        Log.d("id", Variable.email)

        if (Tags.mainFragmentTag != "") {
            rcAdapter.updateAdapter(dataListTag)
        }else{
            rcAdapter.updateAdapter(dataList)
        }




    }




    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}