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
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.custonEnqueue
import kotlinx.android.synthetic.main.fragment_home_order_edit.*

class HomeOrderEditFragment : Fragment(){

    lateinit var homeOrderEditAdapter : HomeOrderEditAdapter
    var datas_home = mutableListOf<HomeOrderData>()

    val requestToServer = RequestToServer
    lateinit var count_litener : CountChangeListener

    // 변경된 아이템 저장
    val changed_items = mutableMapOf<Int,Int>()

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

        count_litener = object  : CountChangeListener{
            override fun onChange(position: Int, value: Int) {
                changed_items[position]=value
            }
        }

        homeOrderEditAdapter = HomeOrderEditAdapter(view.context)
        homeOrderEditAdapter.set_listener(count_litener)
        rv_home_order_edit.adapter = homeOrderEditAdapter
        rv_home_order_edit.addItemDecoration(HomeOrderRecyclerViewDecoration())
        //loadHomeOrderDatas()

        //목록 통신
        homeMemoEditResponse()

        //완료 버튼 누르면 프래그먼트 제거
        edit_tv_edit_memo.setOnClickListener {

            Log.d("homeordereditFragment",changed_items.toString())

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

    /*
    //홈 메모 수정 완료 통신
    private fun homeEditResponse() {
        val postHomeEditResponse = networkService.postHomeEditResponse("application/json")

        postHomeEditResponse.enqueue(object : Callback<PostHomeEditResponse> {
            override fun onFailure(call: Call<PostHomeEditResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<PostHomeEditResponse>,
                response: Response<PostHomeEditResponse>
            ) {

            }
        })
    }*/

    //홈 메모 수정 프래그먼트에서 발주 확인 목록 통신
    private fun homeMemoEditResponse() {
        /*val getHomeMemoEditResponse = networkService.getHomeMemoEditResponse("application/json")

        getHomeMemoEditResponse.enqueue(object : Callback<GetHomeMemoEditResponse> {
            override fun onFailure(call: Call<GettHomeMemoEditResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<GetHomeMemoEditResponse>,
                response: Response<GetHomeMemoEditResponse>
            ) {

            }
        })*/

        requestToServer.service.getHomeOrderResponse(
            getString(R.string.test_token)
        ).custonEnqueue(
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

    //발주 확인
    /*private fun loadHomeOrderDatas(){
        datas.apply {
            add(
                HomeOrderData(
                    index = 0,
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "우유",
                    count = 10,
                    unit = "팩"
                )
            )
            add(
                HomeOrderData(
                    index = 1,
                    img = "https://cdn.pixabay.com/photo/2020/05/03/13/09/puppy-5124947_1280.jpg",
                    name = "녹차 파우더",
                    count = 5,
                    unit = "팩"
                )
            )
            add(
                HomeOrderData(
                    index = 2,
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "딸기",
                    count = 8,
                    unit = "팩"
                )
            )
            add(
                HomeOrderData(
                    index = 3,
                    img = "https://cdn.pixabay.com/photo/2020/05/03/13/09/puppy-5124947_1280.jpg",
                    name = "모카 파우더",
                    count = 10,
                    unit = "팩"
                )
            )
            add(
                HomeOrderData(
                    index = 4,
                    img = "https://cdn.pixabay.com/photo/2020/05/03/13/09/puppy-5124947_1280.jpg",
                    name = "원두",
                    count = 4,
                    unit = "팩"
                )
            )
            add(
                HomeOrderData(
                    index = 5,
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "헤이즐넛 시럽",
                    count = 2,
                    unit = "팩"
                )
            )
            add(
                HomeOrderData(
                    index = 6,
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "우유",
                    count = 10,
                    unit = "팩"
                )
            )
            add(
                HomeOrderData(
                    index = 7,
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "딸기",
                    count = 10,
                    unit = "팩"
                )
            )
            add(
                HomeOrderData(
                    index = 8,
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "원두",
                    count = 10,
                    unit = "팩"
                )
            )

        }

        homeOrderEditAdapter.datas = datas
        homeOrderEditAdapter.notifyDataSetChanged()

    }*/

    interface CountChangeListener{
        fun onChange(position: Int, value: Int)
    }

}