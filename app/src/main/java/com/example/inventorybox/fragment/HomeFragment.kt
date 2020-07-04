package com.example.inventorybox.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.inventorybox.Adpater.HomeOrderAdapter
import com.example.inventorybox.Data.HomeOrderData
import com.example.inventorybox.R
import com.example.inventorybox.etc.RecyclerViewDecoration
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_home_orderlist.*
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

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeOrderAdapter = HomeOrderAdapter(view.context)
        rv_home_order.adapter = homeOrderAdapter
        rv_home_order.addItemDecoration(RecyclerViewDecoration())
        loadHomeOrderDatas()

        currentDate()


    }

    //현재 날짜로 세팅
    fun currentDate() {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd E요일").withLocale(Locale.forLanguageTag("ko"))
        val formatted = current.format(formatter)

        Log.d("date", "#############$formatted")

        tv_home_date2.setText(formatted + "입니다.")

    }

    private fun loadHomeOrderDatas(){
        datas.apply {
            add(
                HomeOrderData(
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "우유",
                    count = 10
                )
            )
            add(
                HomeOrderData(
                    img = "https://cdn.pixabay.com/photo/2020/05/03/13/09/puppy-5124947_1280.jpg",
                    name = "녹차 파우더",
                    count = 5
                )
            )
            add(
                HomeOrderData(
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "딸기",
                    count = 8
                )
            )
            add(
                HomeOrderData(
                    img = "https://cdn.pixabay.com/photo/2020/05/03/13/09/puppy-5124947_1280.jpg",
                    name = "모카 파우더",
                    count = 10
                )
            )
            add(
                HomeOrderData(
                    img = "https://cdn.pixabay.com/photo/2020/05/03/13/09/puppy-5124947_1280.jpg",
                    name = "원두",
                    count = 4
                )
            )
            add(
                HomeOrderData(
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "헤이즐넛 시럽",
                    count = 2
                )
            )
            add(
                HomeOrderData(
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "우유",
                    count = 10
                )
            )
        }

        homeOrderAdapter.datas = datas
        homeOrderAdapter.notifyDataSetChanged()

    }
}