package com.example.inventorybox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.inventorybox.activity.LoginActivity
import com.example.inventorybox.adapter.OnboardingPagerAdapter
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoarding : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        // dot indicator(tab layout)와 viewpager 연결
        tab_onBoarding.setupWithViewPager(viewPager_onBoarding)
        // Viewpager와 Custom Pager Adapter 연결
        viewPager_onBoarding.adapter=OnboardingPagerAdapter(supportFragmentManager)

        // 다음 뷰페이저로 이동
        btn_onboard_next.setOnClickListener {
            if(viewPager_onBoarding.currentItem==3){
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }else{
                viewPager_onBoarding.currentItem = viewPager_onBoarding.currentItem+1
            }
        }

    }
}