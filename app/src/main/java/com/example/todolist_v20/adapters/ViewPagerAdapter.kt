package com.example.todolist_v20.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.todolist_v20.fragments.ContentTabFragment

class ViewPagerAdapter(frag: AppCompatActivity, private val list: List<Fragment>):FragmentStateAdapter(frag) {     // Наследуемся от FragmentStateAdapter который буде использовать список из фрагментов //
    override fun getItemCount(): Int {                                                                            // Сюда передаем количество элементов которое будет перелистовать ViewPager2 //
        return list.size                                                                                          // Возвращаем количестов элементов в списке//
    }

    override fun createFragment(position: Int): Fragment {                                                        // Здесь фрагметы будут создаваться используя позиции списка//
        return list[position]                                                                                     // Возвращаем позицию из списка //
    }
}