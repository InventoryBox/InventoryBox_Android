package com.example.inventorybox.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.inventorybox.R
import com.example.inventorybox.data.HomeOrderData
import com.example.inventorybox.fragment.HomeViewPager

class CustomPagerAdapter(fm:FragmentManager, data: MutableList<HomeOrderData>):
FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    var datas = data

    override fun getItem(position: Int): Fragment {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        return datas.size / 14 + 1
    }
}