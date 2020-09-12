package com.example.inventorybox.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.inventorybox.FragmentOnboarding

class OnboardingPagerAdapter (fm : FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getItem(position: Int): Fragment {
        return FragmentOnboarding(position)
    }

    override fun getCount(): Int {
        return 4
    }
}