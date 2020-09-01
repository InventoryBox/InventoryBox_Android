package com.example.inventorybox.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.inventorybox.R
import com.example.inventorybox.fragment.*
import com.example.inventorybox.network.RequestToServer
import kotlinx.android.synthetic.main.activity_find_email.*

class FindEmailActivity : AppCompatActivity() {

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_email)

        find_email_viewPager.adapter = FindEmailPagerAdapter(supportFragmentManager)

        btn_back_find_email.setOnClickListener {
            finish()
        }
    }

}

private class FindEmailPagerAdapter(fm: FragmentManager):
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0-> FindEmailFragment()
            else-> FindPasswordFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0->return "이메일"
            else->return "비밀번호"
        }
    }

}