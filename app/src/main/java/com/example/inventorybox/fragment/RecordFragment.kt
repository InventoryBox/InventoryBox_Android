package com.example.inventorybox.fragment

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
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
import com.example.inventorybox.etc.RecordDatePicker
import com.example.inventorybox.etc.RecordDatePicker.Companion.cal
import kotlinx.android.synthetic.main.fragment_record.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class RecordFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var recordCompletedAdapter: RecordCompletedAdapter
    var datas = mutableListOf<RecordCompletedData>()

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
        loadRecordCompletedDatas()

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

        //카테고리 선택 뷰
        val datas_cate= mutableListOf<String>("전체","액체류","파우더류","과일류","치킨류","라떼류")

        val category_adapter = RecordCategoryAdapter(view.context)
        category_adapter.datas = datas_cate
        rv_record_cate.adapter = category_adapter


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

    private fun loadRecordCompletedDatas(){
        datas.apply {
            add(
                RecordCompletedData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    unit = "덩어리",
                    count_noti = 500,
                    count_stock = 0
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

    //현재 날짜로 세팅
    fun currentDate() {
        val current = LocalDateTime.now()
        val month = DateTimeFormatter.ofPattern("yyyy.MM.")
        val date = DateTimeFormatter.ofPattern("dd ")
        val day = DateTimeFormatter.ofPattern("E요일").withLocale(Locale.forLanguageTag("ko"))
        val mydate = current.format(month).toString()+current.format(date).toString()+current.format(day).toString()

        tv_date.setText(mydate)

    }

}