package com.inventorybox.inventorybox.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.inventorybox.inventorybox.data.HomeOrderData
import com.inventorybox.inventorybox.fragment.HomeViewPager

class CustomPagerAdapter(fm:FragmentManager, val datas: MutableList<HomeOrderData>):
FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){


    override fun getItem(position: Int): Fragment {
//        Log.d("###HomeFragment - CustomPagerAdapter", datas.subList(position*14,  (position+1)*14+1).toString())
        var min = position * 14
        var max = if(datas.size> (position+1)*14) (position+1)*14 else datas.size
        return HomeViewPager(datas.subList(min, max))
    }

    override fun getCount(): Int {
        return datas.size / 14 + 1
    }
}