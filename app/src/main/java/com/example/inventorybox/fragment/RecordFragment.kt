package com.example.inventorybox.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.inventorybox.Adpater.RecordCompletedAdapter
import com.example.inventorybox.data.RecordCompletedData

import com.example.inventorybox.R
import com.example.inventorybox.activity.RecordAddActivity
import com.example.inventorybox.activity.RecordCateogyActivity
import com.example.inventorybox.activity.RecordRecordActivity
import com.example.inventorybox.adapter.RecordAddAdapter
import com.example.inventorybox.adapter.RecordCategoryAdapter
import com.example.inventorybox.data.RecordAddData
import com.example.inventorybox.data.RecordCategoryData
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_record.*
import kotlinx.android.synthetic.main.item_record_edit.*
import kotlinx.android.synthetic.main.item_record_record.*


class RecordFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var recordCompletedAdapter: RecordCompletedAdapter
    var datas = mutableListOf<RecordCompletedData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //재고 기록 첫 화면
        recordCompletedAdapter = RecordCompletedAdapter(view.context)
        rv_record_completed.adapter = recordCompletedAdapter
        loadRecordCompletedDatas()

        //버튼 눌렀을 때 최상단으로 이동
        btn_up.setOnClickListener {
            scrollview_record.smoothScrollTo(0, 0)
        }

        //재고 기록하기 버튼 클릭시 '재고기록' 액티비티 띄우기
        btn_record.setOnClickListener {
            activity?.let{
                    val intent = Intent (it, RecordRecordActivity::class.java)
                it.startActivity(intent)
            }
        }

        //재료 기록하기 버튼 클릭시 '재료추가' 액티비티 띄우기
        tv_plus.setOnClickListener{
            activity?.let{
                val intent = Intent (it, RecordAddActivity::class.java)
                it.startActivity(intent)
            }
        }

        //카테고리 추가 이미지 선택시 '카테고리 추가' 액티비티 띄우기
        img_folderplus.setOnClickListener {
            activity?.let{
                val intent = Intent (it, RecordCateogyActivity::class.java)
                it.startActivity(intent)
            }
        }
    }

    private fun loadRecordCompletedDatas(){
        datas.apply {
            add(
                RecordCompletedData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    unit = "덩어리",
                    count_noti = 500,
                    count_stock = 3
                )
            )

            add(
                RecordCompletedData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    unit = "덩어리",
                    count_noti = 200,
                    count_stock = 3
                )
            )

            add(
                RecordCompletedData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    unit = "덩어리",
                    count_noti = 100,
                    count_stock = 3
                )
            )

            add(
                RecordCompletedData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    unit = "덩어리",
                    count_noti = 500,
                    count_stock = 3
                )
            )

        }

        recordCompletedAdapter.datas = datas
        recordCompletedAdapter.notifyDataSetChanged()

    }



}