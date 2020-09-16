package com.example.inventorybox.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.inventorybox.DB.SharedPreferenceController
import com.example.inventorybox.ExchangeMyLike
import com.example.inventorybox.ExchangeMyPost
import com.example.inventorybox.ExchangeSearch
import com.example.inventorybox.R
import com.example.inventorybox.activity.ExchangePostActivity
import com.example.inventorybox.activity.ExchangeSetLocation
import com.example.inventorybox.etc.showCustomToast
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
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

        // 검색 누르면 검색창 뜨도록
        btn_exchange_search.setOnClickListener {
            val fragment = ExchangeSearch()
            val transaction = fragmentManager!!.beginTransaction()
            transaction.add(R.id.frame_layout, fragment, "graph")
            transaction.addToBackStack(null) //해당 transaction을 백스택에 저장
            transaction.commit() //transaction 실행
        }
        //내가 쓴 게시물
        iv_exchange_mypost.setOnClickListener {
            val intent = Intent(it.context, ExchangeMyPost::class.java)
            startActivity(intent)
        }

        exchange_iv_floating_btn.setOnClickListener {
            val intent = Intent(it.context, ExchangePostActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivityForResult(intent, 0)
//            it.context.startActivity(intent)
        }

        // 위치 설정하기로 이동하기
        tv_set_location.setOnClickListener {
            val intent = Intent(it.context, ExchangeSetLocation::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            it.context.startActivity(intent)
        }

        // 내가 찜한 게시물
        btn_mypost.setOnClickListener {
            val fragment = ExchangeMyLike()
            val transaction = fragmentManager!!.beginTransaction()
            transaction.add(R.id.frame_layout, fragment, "graph")
            transaction.addToBackStack(null) //해당 transaction을 백스택에 저장
            transaction.commit() //transaction 실행
        }

    }

    override fun onStart() {
        super.onStart()
        RequestToServer.service.requestExchangeHomeData(
            SharedPreferenceController.getUserToken(context!!),
            1
        ).customEnqueue(
            onSuccess = {
                tv_set_location?.text = it.data.addressInfo
                if(it.data.addressInfo==null){
//                    val intent = Intent(view?.context, ExchangeSetLocation::class.java)
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    view?.context!!.startActivity(intent)
                    tv_set_location.text = "터치해서 가게위치 설정하기"
                }
            },
                onError = {
                    SharedPreferenceController.clearUserToken(context!!)
                    //MainActivity로 전달 - MainActivity 끝내기
                    context!!.showCustomToast("토큰이 만료되어 재로그인합니다.")
                    val intent = Intent("finish_activity")
                    context?.sendBroadcast(intent)

                }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val transaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.frame_layout, ExchangeFragment(), "record")
            .commit()
    }

}

private class PagerAdapter(fm:FragmentManager):
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0->ExchangeAllFragment()
            1->ExchangeFoodFragment()
            else->ExchangeProductFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0->return "전체"
            1->return "식품"
            else->return "공산품"
        }
    }

}