package com.example.inventorybox.fragment

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.example.inventorybox.Adpater.RecordCompletedAdapter
import com.example.inventorybox.data.RecordCompletedData

import com.example.inventorybox.R
import com.example.inventorybox.activity.RecordAddActivity
import com.example.inventorybox.activity.RecordCateogyActivity
import com.example.inventorybox.activity.RecordModifyActivity
import com.example.inventorybox.activity.RecordRecordActivity
import com.example.inventorybox.adapter.RecordCategoryAdapter
import com.example.inventorybox.data.RecordHomeCategoryInfo
import com.example.inventorybox.data.RecordHomeItemInfo
import com.example.inventorybox.etc.RecordDatePicker
import com.example.inventorybox.etc.RecordDatePicker.Companion.cal
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.fragment_record.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class RecordFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var recordCompletedAdapter: RecordCompletedAdapter
    var datas_cate = mutableListOf<RecordHomeCategoryInfo>()
    var datas_item = mutableListOf<RecordHomeItemInfo>()
    var sorted_item = mutableListOf<RecordHomeItemInfo>()

    lateinit var category_adapter : RecordCategoryAdapter

    val category_listener = object : CategoryClickListener{
        override fun onClick(category_idx: Int) {
            if(category_idx>1){
                sorted_item = datas_item.filter {
                    it.categoryIdx == category_idx
                }.toMutableList()
            }else{
                sorted_item = datas_item
            }
            recordCompletedAdapter.datas = sorted_item
            recordCompletedAdapter.notifyDataSetChanged()

        }
    }


    val requestToServer = RequestToServer

    val datepicker_listener: DatePickerDialog.OnDateSetListener = object  : DatePickerDialog.OnDateSetListener{
        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, p3: Int) {
            val DAYS = arrayListOf<String>("일","월","화","수","목","금","토")

            //datepicker 날짜로 calendar 세팅하기
            cal.set(year, month, p3)

            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val date = cal.get(Calendar.DATE)
            val day = DAYS.get(cal.get(Calendar.DAY_OF_WEEK))

            val mydate = year.toString() +"."+ month.toString() +"."+ date.toString() +" "+ day +"요일"
            tv_date.setText(mydate)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //현재 날짜로 세팅
        currentDate()

        //재고 기록 첫 화면
        recordCompletedAdapter = RecordCompletedAdapter(view.context)
        rv_record_completed.adapter = recordCompletedAdapter

        //상단 카테고리
        category_adapter = RecordCategoryAdapter(view.context)
        category_adapter.listener = category_listener
        category_adapter.datas = datas_cate
        rv_record_cate.adapter = category_adapter

        //버튼 눌렀을 때 최상단으로 이동
        btn_up.setOnClickListener {
            scrollview_record.smoothScrollTo(0, 0)
        }

        //날짜 눌렀을 때 날짜 선택하는 datepicker 나오기
        cL_date.setOnClickListener {
            val pd = RecordDatePicker()
            pd.show(requireFragmentManager(), "datePicker")
            pd.setListener(datepicker_listener)

        }

        //데이터 가져오기
        RecordHomeResponse()

        //재고 기록하기 버튼 클릭시 '재고기록' 액티비티 띄우기
        btn_record.setOnClickListener {
            activity?.let{
                val intent = Intent (it, RecordRecordActivity::class.java)
                it.startActivity(intent)
            }
        }

        //재료 추가하기 버튼 클릭시 '재료추가' 액티비티 띄우기
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

        //재료 수정하기 버튼 클릭시 '재료수정' 액티비티 띄우기
        tv_modify.setOnClickListener{
            activity?.let{
                val intent = Intent (it, RecordModifyActivity::class.java)
                it.startActivity(intent)
            }
        }
    }


    private fun RecordHomeResponse(){

        requestToServer.service.getRecordHomeResponse(
            0, getString(R.string.test_token)
        ).customEnqueue(
            onSuccess = {

                for(data in it.data.categoryInfo){
                    datas_cate.add(data)
                }
                category_adapter.datas = datas_cate
                category_adapter.notifyDataSetChanged()

                for(data in it.data.itemInfo){
                    datas_item.add(data)

                }
                recordCompletedAdapter.datas = datas_item
                recordCompletedAdapter.notifyDataSetChanged()

                var isRecorded = it.data.isRecorded
                if (isRecorded == 1) {
                    //btn_record.visibility = View.GONE
                }

                var isAddBtn = it.data.addButton
                if (isAddBtn == 0){
                    //tv_plus.visibility = View.INVISIBLE
                    tv_plus.visibility = View.VISIBLE
                }

                var recentDate = it.data.date
                tv_date.setText(recentDate)
            }
        )

        requestToServer.service.getRecordHomeResponse(
            1, getString(R.string.test_token)
        ).customEnqueue(
            onSuccess = {
                for(data in it.data.categoryInfo){
                    datas_cate.add(data)
                }
                category_adapter.datas = datas_cate
                category_adapter.notifyDataSetChanged()

                for(data in it.data.itemInfo){
                    datas_item.add(data)

                }
                recordCompletedAdapter.datas = datas_item
                recordCompletedAdapter.notifyDataSetChanged()

                var isRecorded = it.data.isRecorded
                if (isRecorded == 1) {
                    btn_record.visibility = View.GONE
                }else{
                }

                var isAddBtn = it.data.addButton
                if (isAddBtn == 0){
                    //tv_plus.visibility = View.INVISIBLE
                    tv_plus.visibility = View.VISIBLE
                }


            }
        )
    }


    //현재 날짜로 세팅
    fun currentDate() {
        val current = LocalDateTime.now()
        val month = DateTimeFormatter.ofPattern("yyyy.MM.")
        val date = DateTimeFormatter.ofPattern("dd ")
        val day = DateTimeFormatter.ofPattern("E요일").withLocale(Locale.forLanguageTag("ko"))
        val mydate = current.format(month).toString()+current.format(date).toString()+current.format(day).toString()

        tv_date.setText(mydate)

    }
    interface CategoryClickListener{
        fun onClick(category_idx : Int)
    }

}