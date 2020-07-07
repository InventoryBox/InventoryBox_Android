package com.example.inventorybox.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.inventorybox.adapter.HomeOrderAdapter
import com.example.inventorybox.adapter.HomeTodayOrderAdapter
import com.example.inventorybox.data.HomeOrderData
import com.example.inventorybox.R
import com.example.inventorybox.etc.HomeOrderRecyclerViewDecoration
import com.example.inventorybox.etc.HomeTodayRecyclerViewDecoration
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    lateinit var homeOrderAdapter : HomeOrderAdapter
    var datas = mutableListOf<HomeOrderData>()

    lateinit var homeTodayOrderAdapter: HomeTodayOrderAdapter
    //var datas2 = mutableListOf<HomeTodayOrderData>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //오늘 발주할 재고 확인
        homeTodayOrderAdapter = HomeTodayOrderAdapter(view.context)
        rv_home_today_order.adapter = homeTodayOrderAdapter
        rv_home_today_order.addItemDecoration(HomeTodayRecyclerViewDecoration())
        homeTodayOrderAdapter.datas = datas
        homeTodayOrderAdapter.notifyDataSetChanged()


        //발주 확인
       homeOrderAdapter = HomeOrderAdapter(view.context)
        rv_home_order.adapter = homeOrderAdapter
        rv_home_order.addItemDecoration(HomeOrderRecyclerViewDecoration())
        loadHomeOrderDatas()

        currentDate()

        //리사이클러뷰 스크롤 중복 막기
        rv_home_order.setOverScrollMode(View.OVER_SCROLL_NEVER)

        //플로팅 버튼 눌렀을 때 최상단으로 이동
        iv_floating_btn.setOnClickListener {
            scrollview_home.smoothScrollTo(0, 0)
        }

        /*btn_toolbar_home.setOnClickListener {
            home_drawer.openDrawer(drawer)
        }*/

        //메모 수정 클릭했을 때 새로운 프래그먼트로
        tv_edit_memo.setOnClickListener {
            val fragment = HomeOrderEditFragment()
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.add(R.id.frame_layout, fragment, "homeOrdercheck")
            transaction.addToBackStack(null) //해당 transaction을 백스택에 저장
            transaction.commit() //transaction 실행
        }

    }

    //현재 날짜로 세팅
    fun currentDate() {
        val current = LocalDateTime.now()
        val month = DateTimeFormatter.ofPattern("yyyy년 MM월")
        val date = DateTimeFormatter.ofPattern("dd")
        val day = DateTimeFormatter.ofPattern("E요일").withLocale(Locale.forLanguageTag("ko"))
        val formatted = current.format(month)
        val formatted2 = current.format(date)
        val formatted3 = current.format(day)

        //Log.d("date", "#############$formatted")

        tv_home_month.setText(formatted)
        tv_home_date.setText(formatted2)
        tv_home_day.setText(formatted3)

    }

    //오늘 발주할 재고 확인
    /*private fun loadHomeTodayOrderDatas(){
        datas2.apply {
            add(
               HomeTodayOrderData(
                   index = 0,
                   name = "우유kgjslkgjlkj"
               )
            )
            add(
                HomeTodayOrderData(
                    index = 1,
                    name = "녹차 파우더"
                )
            )
            add(
                HomeTodayOrderData(
                    index = 2,
                    name = "딸기"
                )
            )
            add(
                HomeTodayOrderData(
                    index = 3,
                    name = "모카 파우더"
                )
            )
            add(
                HomeTodayOrderData(
                    index = 4,
                    name = "원두"
                )
            )
        }

        homeTodayOrderAdapter.datas = datas2
        homeTodayOrderAdapter.notifyDataSetChanged()
    }*/

    //발주 확인
    private fun loadHomeOrderDatas(){
        datas.apply {
            add(
                HomeOrderData(
                    index = 0,
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "우유",
                    count = 10
                )
            )
            add(
                HomeOrderData(
                    index = 1,
                    img = "https://cdn.pixabay.com/photo/2020/05/03/13/09/puppy-5124947_1280.jpg",
                    name = "녹차 파우더",
                    count = 5
                )
            )
            add(
                HomeOrderData(
                    index = 2,
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "딸기",
                    count = 8
                )
            )
            add(
                HomeOrderData(
                    index = 3,
                    img = "https://cdn.pixabay.com/photo/2020/05/03/13/09/puppy-5124947_1280.jpg",
                    name = "모카 파우더",
                    count = 10
                )
            )
            add(
                HomeOrderData(
                    index = 4,
                    img = "https://cdn.pixabay.com/photo/2020/05/03/13/09/puppy-5124947_1280.jpg",
                    name = "원두",
                    count = 4
                )
            )
            add(
                HomeOrderData(
                    index = 5,
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "헤이즐넛 시럽",
                    count = 2
                )
            )
            add(
                HomeOrderData(
                    index = 6,
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "><",
                    count = 10
                )
            )
            add(
                HomeOrderData(
                    index = 6,
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "우유",
                    count = 10
                )
            )
            add(
                HomeOrderData(
                    index = 6,
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "딸기",
                    count = 10
                )
            )
            add(
                HomeOrderData(
                    index = 6,
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "><",
                    count = 10
                )
            )
            add(
                HomeOrderData(
                    index = 6,
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "><",
                    count = 10
                )
            )

        }

        homeOrderAdapter.datas = datas
        homeOrderAdapter.notifyDataSetChanged()

    }
}