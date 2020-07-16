package com.example.inventorybox.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.inventorybox.R
import com.example.inventorybox.activity.ExchangePostActivity
import com.example.inventorybox.activity.ExchangeSetLocation
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.custonEnqueue
import kotlinx.android.synthetic.main.fragment_exchange.*


class ExchangeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exchange, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exchange_viewPager.adapter = PagerAdapter(childFragmentManager)
        exchange_top_navigation.setupWithViewPager(exchange_viewPager)

        exchange_iv_floating_btn.setOnClickListener {
            val intent = Intent(it.context, ExchangePostActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            it.context.startActivity(intent)
        }

        // 위치 설정하기로 이동하기
        tv_set_location.setOnClickListener {
            val intent = Intent(it.context, ExchangeSetLocation::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            it.context.startActivity(intent)
        }

    }

}

private class PagerAdapter(fm:FragmentManager):
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0->ExchangeAllFragment()
            1->ExchangeFoodFragment()
            2->ExchangeProductFragment()
            else->ExchangeRecentFragment()
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0->return "전체"
            1->return "식품"
            2->return "공산품"
            else->return "최근 본 제품"
        }
    }

}