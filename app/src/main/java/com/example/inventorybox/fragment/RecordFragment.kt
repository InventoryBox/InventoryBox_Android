package com.example.inventorybox.fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.core.view.isVisible
import com.example.inventorybox.Adpater.RecordCompletedAdapter
import com.example.inventorybox.DB.SharedPreferenceController
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
import kotlinx.android.synthetic.main.activity_category_edit.*
import kotlinx.android.synthetic.main.fragment_record.*
import kotlinx.android.synthetic.main.fragment_record.cL_date
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

    var m_year : String = ""
    var m_month : String = ""
    var m_date : String = ""

    var recentDate = ""
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
            val DAYS = arrayListOf<String>("일","월","화","수","목","금","토","일")

            //datepicker 날짜로 calendar 세팅하기
            cal.set(year, month-1, p3)

            val today = Calendar.getInstance()
            //오늘 날짜일때만 카테고리 설정 보이도록
            if(cal.get(Calendar.YEAR)==today.get(Calendar.YEAR) && cal.get(Calendar.MONTH)==today.get(Calendar.MONTH) && cal.get(Calendar.DATE)==today.get(Calendar.DATE)){
                img_folderplus.visibility = View.VISIBLE
            }else{
                img_folderplus.visibility = View.INVISIBLE
            }


            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)+1
            var new_month = if(month<10) "0"+month else month.toString()
            val date = cal.get(Calendar.DATE)
            val new_date = if(date<10) "0"+date else date.toString()
            val day = DAYS.get(cal.get(Calendar.DAY_OF_WEEK)-1)
//            val day =cal.get(Calendar.DAY_OF_WEEK)
            m_year = year.toString()
            m_month = new_month
            m_date = new_date
            recentDate = "$m_year-$m_month-$m_date"

            val mydate = year.toString() +"."+ new_month.toString() +"."+ new_date.toString() +" "+ day +"요일"
            tv_date.setText(mydate)
            requestData(year.toString(), new_month, new_date)
            Log.d("testtest","$year, $new_month, $new_date")

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
//        RecordHomeResponse()
        requestDefaultData()

        //재고 기록하기 버튼 클릭시 '재고기록' 액티비티 띄우기
        btn_record.setOnClickListener {
            activity?.let{
                val intent = Intent (it, RecordRecordActivity::class.java)
                startActivityForResult(intent,0)
            }
        }

//        //재료 추가하기 버튼 클릭시 '재료추가' 액티비티 띄우기
//        record_btn_add_ingredient.setOnClickListener{
//            activity?.let{
//                val intent = Intent (it, RecordAddActivity::class.java)
//                startActivityForResult(intent, 0)
//            }
//        }

        //카테고리 추가 이미지 선택시 '카테고리 추가' 액티비티 띄우기
        img_folderplus.setOnClickListener {
            activity?.let{
                val intent = Intent (it, RecordCateogyActivity::class.java)
                intent.putExtra("date", "$m_year-$m_month-$m_date")
                startActivityForResult(intent, 0)

            }
        }

        //기록 수정하기 버튼 클릭시 '재료수정' 액티비티 띄우기
        tv_modify.setOnClickListener{
            activity?.let{
                val intent = Intent (it, RecordModifyActivity::class.java)
                intent.putExtra("date", recentDate)
                startActivityForResult(intent, 0)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val transaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.frame_layout, RecordFragment(), "record")
            .commit()
    }

//
//    private fun RecordHomeResponse(){
////
////        requestToServer.service.getRecordHomeResponse(
////            "2020-07-17", getString(R.string.test_token)
////        ).customEnqueue(
////            onSuccess = {
////
////                for(data in it.data.categoryInfo){
////                    datas_cate.add(data)
////                }
////                category_adapter.datas = datas_cate
////                category_adapter.notifyDataSetChanged()
////
////                for(data in it.data.itemInfo){
////                    datas_item.add(data)
////
////                }
////                recordCompletedAdapter.datas = datas_item
////                recordCompletedAdapter.notifyDataSetChanged()
////
////                var isRecorded = it.data.isRecorded
////                if (isRecorded == 1) {
////                    //btn_record.visibility = View.GONE
////                    btn_record.visibility = View.VISIBLE
////                }
////
////                var isAddBtn = it.data.addButton
////                if (isAddBtn == 0){
////                    //tv_plus.visibility = View.INVISIBLE
////                    tv_plus.visibility = View.VISIBLE
////                }
////
////                var recentDate = it.data.date
////                tv_date.setText(recentDate)
////            }
////        )
////
////        requestToServer.service.getRecordHomeResponse(
////            "2020-07-17", getString(R.string.test_token)
////        ).customEnqueue(
////            onSuccess = {
////                for(data in it.data.categoryInfo){
////                    datas_cate.add(data)
////                }₩
////                category_adapter.datas = datas_cate
////                category_adapter.notifyDataSetChanged()
////
////                for(data in it.data.itemInfo){
////                    datas_item.add(data)
////
////                }
////                recordCompletedAdapter.datas = datas_item
////                recordCompletedAdapter.notifyDataSetChanged()
////
////                var isRecorded = it.data.isRecorded
////                if (isRecorded == 1) {
////                    //btn_record.visibility = View.GONE
////                    btn_record.visibility = View.VISIBLE
////                }else{
////                }
////
////                var isAddBtn = it.data.addButton
////                if (isAddBtn == 0){
////                    //tv_plus.visibility = View.INVISIBLE
////                    tv_plus.visibility = View.VISIBLE
////                }
////
////
////            }
////        )
//    }

    fun requestDefaultData(){
        requestToServer.service.getRecordHomeResponse(
            "0", SharedPreferenceController.getUserToken(context!!)
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


                //데이터가 없을 경우 로고 화면 띄우기
                if(datas_item.size==0){
                    rv_record_completed.visibility = View.GONE
                    cl_no_data.visibility = View.VISIBLE
                }else{
                    rv_record_completed.visibility = View.VISIBLE
                    cl_no_data.visibility = View.GONE
                }

                var isRecorded = it.data.isRecorded
                if (isRecorded == 1) {
                    btn_record?.visibility = View.GONE
                }else{
                    btn_record.visibility = View.VISIBLE
                }

               // addButton true(1)일 때만 재료추가하기 나타남
                var isAddBtn = it.data.addButton
//                if (isAddBtn == 0){
//                    record_btn_add_ingredient.visibility = View.INVISIBLE
////                    tv_plus.isClickable = false
//                }else{
//                    record_btn_add_ingredient.visibility = View.VISIBLE
//                }

                recentDate = it.data.date
                tv_date.setText(recentDate)
            }
        )
    }

    fun requestData(year : String, month : String, date : String){

        val date = "$year-$month-$date"

        datas_cate = mutableListOf()
        datas_item = mutableListOf()
        requestToServer.service.getRecordHomeResponse(
            date, SharedPreferenceController.getUserToken(context!!)
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

                //데이터가 없을 경우 로고 화면 띄우기
                if(datas_item.size==0){
                    rv_record_completed.visibility = View.GONE
                    cl_no_data.visibility = View.VISIBLE
                }else{
                    rv_record_completed.visibility = View.VISIBLE
                    cl_no_data.visibility = View.GONE
                }

                var isRecorded = it.data.isRecorded
                if (isRecorded == 1) {
                    btn_record.visibility = View.GONE
                }else{
//                    btn_record.visibility = View.VISIBLE

                }

                var isAddBtn = it.data.addButton
//                if (isAddBtn == 0){
//                    tv_plus.visibility = View.INVISIBLE
////                    tv_plus.visibility = View.VISIBLE
//                }

//
                view!!.invalidate()
//                var recentDate = it.data.date
//                tv_date.setText(recentDate)
            }
        )
    }


    //현재 날짜로 세팅
    fun currentDate() {
        val current = LocalDateTime.now()
        m_year =  current.format(DateTimeFormatter.ofPattern("yyyy")).toString()
        m_month =  current.format(DateTimeFormatter.ofPattern("MM")).toString()
//        m_date =   current.format(DateTimeFormatter.ofPattern("dd")).toString()
        m_date= "17"
        val month =  DateTimeFormatter.ofPattern("yyyy.MM.")
        val date = DateTimeFormatter.ofPattern("dd ")
        val day = DateTimeFormatter.ofPattern("E요일").withLocale(Locale.forLanguageTag("ko"))
        val mydate = current.format(month).toString()+current.format(date).toString()+current.format(day).toString()

        tv_date.setText(mydate)

    }
    interface CategoryClickListener{
        fun onClick(category_idx : Int)
    }
}