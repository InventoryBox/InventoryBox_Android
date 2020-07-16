package com.example.inventorybox.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.inventorybox.R
import com.example.inventorybox.adapter.HomeOrderEditAdapter
import com.example.inventorybox.data.HomeOrderData
import com.example.inventorybox.etc.HomeOrderRecyclerViewDecoration
import com.example.inventorybox.network.PUT.RequestMemo
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.fragment_home_order_edit.*

class HomeOrderEditFragment : Fragment(){

    lateinit var homeOrderEditAdapter : HomeOrderEditAdapter
    var datas_home = mutableListOf<HomeOrderData>()

    var item_idx = -1

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_order_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeOrderEditAdapter = HomeOrderEditAdapter(view.context)
        rv_home_order_edit.adapter = homeOrderEditAdapter
        rv_home_order_edit.addItemDecoration(HomeOrderRecyclerViewDecoration())
        //loadHomeOrderDatas()

        //목록 통신
        homeMemoEditResponse()

        //완료 버튼 누르면 프래그먼트 제거
        edit_tv_edit_memo.setOnClickListener {
            homeEditResponse()

            val fragment = HomeOrderEditFragment()
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.remove(this).commit()
        }

        //플로팅 버튼 눌렀을 때 최상단으로 이동
        edit_iv_floating_btn.setOnClickListener {
            scrollview_home_edit.smoothScrollTo(0, 0)
        }

        //리사이클러뷰 중복스크롤 막기
        rv_home_order_edit.setOverScrollMode(View.OVER_SCROLL_NEVER)

    }


    //홈 메모 수정 완료 통신
    private fun homeEditResponse() {

        requestToServer.service.requestHomeMemo(
            getString(R.string.test_token),
            RequestMemo(
                itemIdx = 1,
                memoCnt = 21
            )
        ).customEnqueue(
            onSuccess = {
                Log.d("##############", "수정 성공")
            }
        )
    }

    //홈 메모 수정 프래그먼트에서 발주 확인 목록 통신
    private fun homeMemoEditResponse() {

        requestToServer.service.getHomeOrderResponse(
            getString(R.string.test_token)
        ).customEnqueue(
            onSuccess = {
                Log.d("##############", "성공2")
                for(data in it.data.result){
                    datas_home.add(data)
                }

                //발주 확인
                homeOrderEditAdapter.datas = datas_home
                homeOrderEditAdapter.notifyDataSetChanged()

            }
        )
    }



}