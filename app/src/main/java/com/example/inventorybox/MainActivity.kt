package com.example.inventorybox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.inventorybox.activity.RecordAddActivity
import com.example.inventorybox.fragment.GraphFragment
import com.example.inventorybox.fragment.HomeFragment
import com.example.inventorybox.fragment.RecordFragment
import com.example.inventorybox.fragment.btn_int
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(btn_int==1){
            setContentView(R.layout.activity_add)
        }else{
            setContentView(R.layout.activity_main)
        }

        main_bottom_navigation.setItemIconSize(90)  //하단바 아이콘 사이즈


        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout,
            HomeFragment(), "home").commitAllowingStateLoss()

        main_bottom_navigation.setOnNavigationItemSelectedListener {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

            when(it.itemId) {
                R.id.menu_home -> {
                    val fragment = HomeFragment()
                    transaction.replace(R.id.frame_layout, fragment, "home")
                    home_drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)  //drawer가 나오게 하기
                }
                R.id.menu_record -> {
                    val fragment = RecordFragment()
                    transaction.replace(R.id.frame_layout, fragment, "record")
                    home_drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) //drawer가 안나오게 막기
                }
                R.id.menu_graph -> {
                    val fragment = GraphFragment()
                    transaction.replace(R.id.frame_layout, fragment, "graph")
                    home_drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) //drawer가 안나오게 막기
                }
            }
            transaction.commit()
            true
        }
    }
}