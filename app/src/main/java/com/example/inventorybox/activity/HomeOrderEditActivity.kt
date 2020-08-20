package com.example.inventorybox.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.adapter.HomeOrderEditAdapter
import com.example.inventorybox.data.HomeOrderData
import com.example.inventorybox.etc.HomeOrderRecyclerViewDecoration
import com.example.inventorybox.network.PUT.RequestMemo
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.activity_home_order_edit.*
import kotlinx.android.synthetic.main.activity_main.*

class HomeOrderEditActivity : AppCompatActivity() {

    lateinit var homeOrderEditAdapter : HomeOrderEditAdapter
    var datas_home = mutableListOf<HomeOrderData>()

    val requestToServer = RequestToServer
    lateinit var count_litener : CountChangeListener

    // 변경된 아이템 저장
    val changed_items = mutableMapOf<Int,Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_order_edit)


        count_litener = object  : CountChangeListener {
            override fun onChange(position: Int, value: Int) {
                changed_items[position]=value
            }
        }

        homeOrderEditAdapter = HomeOrderEditAdapter(this)
        homeOrderEditAdapter.set_listener(count_litener)
        rv_home_edit.adapter = homeOrderEditAdapter
        rv_home_edit.addItemDecoration(HomeOrderRecyclerViewDecoration())
        //loadHomeOrderDatas()

        //목록 통신
        homeMemoEditResponse()

        //완료 버튼 누르면
        edit_tv_edit_memo.setOnClickListener {
            for ((position, value) in changed_items) {
                homeEditResponse(position, value)
            }

            Log.d("homeordereditFragment",changed_items.toString())


            val drawerEvent = {
                home_drawer.openDrawer(drawer)
            }

            val intent = Intent(this, HomeOrderDetailActivity::class.java)
            startActivity(intent)


        }

        //리사이클러뷰 중복스크롤 막기
        rv_home_edit.setOverScrollMode(View.OVER_SCROLL_NEVER)


    }

    //홈 메모 수정 완료 통신
    private fun homeEditResponse(position: Int, value: Int) {

        requestToServer.service.requestHomeMemo(
            getString(R.string.test_token),
            RequestMemo(
                itemIdx = position,
                memoCnt = value
            )
        ).customEnqueue(
            onSuccess = {
                Log.d("ResponseMemo", "수정 성공")
            }
        )
    }

    //홈 메모 수정 프래그먼트에서 발주 확인 목록 통신
    private fun homeMemoEditResponse() {

        requestToServer.service.getHomeOrderResponse(
            getString(R.string.test_token)
        ).customEnqueue(
            onSuccess = {
                Log.d("responseHomeOrder", "성공")
                for(data in it.data.result){
                    datas_home.add(data)
                }

                //발주 확인
                homeOrderEditAdapter.datas = datas_home
                homeOrderEditAdapter.notifyDataSetChanged()

            }
        )
    }

    /*
    //홈 메모 수정 프래그먼트에서 발주 확인 목록 통신
    private fun homeMemoEditResponse() {

        requestToServer.service.getHomeOrderResponse(
            getString(R.string.test_token)
        ).customEnqueue(
            onSuccess = {
                Log.d("responseHomeOrder", "성공")
                for(data in it.data.result){
                    datas_home.add(data)
                }

                //발주 확인
                homeOrderEditAdapter.datas = datas_home
                homeOrderEditAdapter.notifyDataSetChanged()

            }
        )
    }

     */

    interface CountChangeListener{
        fun onChange(position: Int, value: Int)
    }
}