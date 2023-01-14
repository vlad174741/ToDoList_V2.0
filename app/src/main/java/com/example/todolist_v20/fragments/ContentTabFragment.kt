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
import com.example.todolist_v20.classes.ViewModelMy
import com.example.todolist_v20.adapters.ViewPagerAdapter
import com.example.todolist_v20.databinding.FragmentContentTabBinding
import com.google.android.material.tabs.TabLayoutMediator

@SuppressLint("StaticFieldLeak")
lateinit var bindingContTab: FragmentContentTabBinding

class ContentTabFragment : Fragment() {

    private val fragmentList = listOf(MainFragment.newInstance(),  EditFragment.newInstance(), OptionFragment.newInstance())      //Массив с fragments для ViewPager2 и TabLayout //
    private val fragmentListTitle = listOf("Главная","Создать","Опции")                                                          // Массив с заголовками для вкладок TabLayout //

    private val model: ViewModelMy by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        bindingContTab = FragmentContentTabBinding.inflate(inflater, container, false)
        return bindingContTab.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)







        val adapterVP = ViewPagerAdapter(activity as AppCompatActivity, fragmentList)                                                   // Идентифицируем ViewPager2 адаптор //
        bindingContTab.viewPager.adapter = adapterVP // Присваеиваем свой адаптор элементу ViewPager2 //

        TabLayoutMediator(bindingContTab.tabLayout, bindingContTab.viewPager){          // Нужен для связки TabLayout и ViewPager2. В нем угазываем id элементов. //
                tab, pos -> tab.text = fragmentListTitle[pos]                                                         // Здесь указываем заголовки для вкалдок. Их отслеживание берем по позиции. //
        }.attach()                                                                      // Запускаем TabLayoutMediator //

        bindingContTab.viewPager.offscreenPageLimit = 1
        model.plant.observe(activity as LifecycleOwner){

        }

    }





    companion object {

        @JvmStatic
        fun newInstance() = ContentTabFragment()
    }
}