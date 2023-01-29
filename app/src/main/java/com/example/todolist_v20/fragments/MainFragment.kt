package com.example.todolist_v20.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.todolist_v20.objects.Variable
import com.example.todolist_v20.classes.ViewModelMy
import com.example.todolist_v20.adapters.RecyclerViewAdapter
import com.example.todolist_v20.dataBase.dbContent.DataBaseManager
import com.example.todolist_v20.dataBase.dbContent.VariableDbContent
import com.example.todolist_v20.databinding.FragmentMainBinding
import com.example.todolist_v20.objects.Tags
import com.example.todolist_v20.objects.Variable.dbManager

@SuppressLint("StaticFieldLeak")
lateinit var bindingMainFragment: FragmentMainBinding
@SuppressLint("StaticFieldLeak")
lateinit var rcAdapter: RecyclerViewAdapter
@SuppressLint("StaticFieldLeak")


class MainFragment : Fragment() {
    private val model: ViewModelMy by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("liveFragment", "MainFragment.onAttach")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("liveFragment", "MainFragment.onCreate")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        bindingMainFragment = FragmentMainBinding.inflate(inflater, container, false)
        Log.d("liveFragment", "MainFragment.onCreateView")
        return bindingMainFragment.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcAdapter = RecyclerViewAdapter(ArrayList(), activity as AppCompatActivity)
        dbManager = DataBaseManager(activity as AppCompatActivity)
        dbManager.openDataBase()
        rcViewReadDB()
        buttonsMainFragment()
        bindingMainFragment.recyclerViewMainFragment.adapter = rcAdapter
        bindingMainFragment.recyclerViewMainFragment.setHasFixedSize(true)
        model.plant.observe(activity as LifecycleOwner){
            rcViewReadDB()
        }
        Log.d("liveFragment", "MainFragment.onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d("liveFragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        val dataList = dbManager.readDataBase(Variable.username, VariableDbContent.selectionColumnAccount)
        if(rcAdapter.itemSelectList.isEmpty()) {
            if (Tags.tagUsingMainFragment != "") { Tags.checkTagMainFragment() }
            else { rcAdapter.updateAdapter(dataList) }
        }
        Log.d("liveFragment", "MainFragment.onResume")
    }

    override fun onPause() {
        super.onPause()
        rcAdapter.notifyDataSetChanged()
        Variable.prevPositionRcView = -1
        Log.d("liveFragment", "MainFragment.onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("liveFragment", "MainFragment.onStop")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("liveFragment", "MainFragment.onDestroyView")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("liveFragment", "MainFragment.onDestroy")
    }


    override fun onDetach() {
        super.onDetach()
        Log.d("liveFragment", "MainFragment.onDetach")

    }

    private fun rcViewReadDB() {
        val dataList = dbManager.readDataBase(Variable.username,VariableDbContent.selectionColumnAccount)
        rcAdapter.updateAdapter(dataList)
    }

    private fun buttonsMainFragment(){

        bindingMainFragment.apply {

            radioButtonMainFragmentTagHome.setOnClickListener {
                tagSelection(radioButtonMainFragmentTagHome, Tags.homeTag)
            }
            radioButtonMainFragmentTagShop.setOnClickListener {
                tagSelection(radioButtonMainFragmentTagShop, Tags.shopTag)
            }
            radioButtonMainFragmentTagBank.setOnClickListener {
                tagSelection(radioButtonMainFragmentTagBank, Tags.bankTag)
            }
            radioButtonMainFragmentTagWork.setOnClickListener {
                tagSelection(radioButtonMainFragmentTagWork, Tags.workTag)
            }
            radioButtonMainFragmentTagWeekend.setOnClickListener {
                tagSelection(radioButtonMainFragmentTagWeekend, Tags.weekendTag)
            }
            radioButtonMainFragmentTagSport.setOnClickListener {
                tagSelection(radioButtonMainFragmentTagSport, Tags.sportTag)
            }
        }

        bindingMainFragment.button.setOnClickListener{
            Log.d("idItemSelect", "${Variable.check}")

            if (bindingMainFragment.button.text == "Отмена")
            { rcAdapter.clearItemSelect() }
            else
            { rcAdapter.alertDeleteDialog(0) }
        }

    }

    private fun tagSelection(button: RadioButton, tag: String){
        Tags.tagSelectMainFragment(button, tag)
    }

    fun deleteButton(){

        if (Variable.check==1) {
            bindingMainFragment.button.visibility = View.VISIBLE
            if (rcAdapter.itemSelectList.isEmpty()){
                bindingMainFragment.button.text = "Отмена"
            }else{
                bindingMainFragment.button.text = "Удалить"
            }
        }else{
            bindingMainFragment.button.visibility = View.GONE
        }
    }

    fun scrollRcView(){
        bindingMainFragment.recyclerViewMainFragment.scrollToPosition(rcAdapter.itemCount - 0)
    }

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}