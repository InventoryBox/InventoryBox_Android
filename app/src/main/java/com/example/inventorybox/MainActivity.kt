package com.example.inventorybox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.inventorybox.fragment.GraphFragment
import com.example.inventorybox.fragment.HomeFragment
import com.example.inventorybox.fragment.RecordFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                }
                R.id.menu_record -> {
                    val fragment = RecordFragment()
                    transaction.replace(R.id.frame_layout, fragment, "record")
                }
                R.id.menu_graph -> {
                    val fragment = GraphFragment()
                    transaction.replace(R.id.frame_layout, fragment, "graph")
                }
            }
            transaction.commit()
            true
        }
    }
}

class RecordBtnActivity: Fragment() {

    /*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btn_record.setOnClickListener {
            val intent = Intent (getActivity(), R.layout.activity_record)
            getActivity()?.startActivity(intent)
        }
    }

     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_record, null)
        // 처리
        return view
    }
}