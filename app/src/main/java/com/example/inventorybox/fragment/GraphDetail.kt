package com.example.inventorybox.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.etc.DatePickerMonth
import com.example.inventorybox.R
import com.example.inventorybox.adapter.GraphDetailWeekCalAdapter
import com.example.inventorybox.adapter.GraphDetailWeekGraphAdapter
import com.example.inventorybox.data.GraphSingleWeekData
import com.example.inventorybox.etc.DatePickerWeek
import com.example.inventorybox.getColorFromRes
import com.example.inventorybox.graph.drawDoubleGraph
import kotlinx.android.synthetic.main.fragment_graph_detail.*
import kotlinx.android.synthetic.main.fragment_graph_detail.cal_month
import kotlinx.android.synthetic.main.layout_custom_toast.view.*
import java.text.SimpleDateFormat
import java.util.*

class GraphDetail : Fragment() {



    //date picker 에서 받은 이벤트를 본 fragment 에 전달해주는 listener
    val datepicker_listener: DatePickerDialog.OnDateSetListener = object  : DatePickerDialog.OnDateSetListener{
        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, p3: Int) {
            Log.d("datepicker","year = $year, month = $month")
            cal_month.text=if(month<10) "0"+month.toString() else month.toString()
            cal_year.text=year.toString()
        }
    }

    val compare_datepicker_listener1 = object : DatePickerDialog.OnDateSetListener{
        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, week: Int) {
            Log.d("datepicker","year = $year, month = $month, week = $week")
            printDatesToCompareGraph(true, year, month, week)
        }
    }
    val compare_datepicker_listener2 = object : DatePickerDialog.OnDateSetListener{
        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, week: Int) {
            Log.d("datepicker","year = $year, month = $month, week = $week")
            printDatesToCompareGraph(false, year, month, week)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_graph_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        graph_detail_week_cal.isNestedScrollingEnabled=false
        //product name 설정
        tv_product_name.text="우유"

        // 뒤로가기 버튼
        btn_back.setOnClickListener {
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.remove(this).commit()
        }

        val cal : Calendar = Calendar.getInstance()

        val format = SimpleDateFormat("MM")

        cal_month.text=format.format(cal.time)
        cal_year.text=cal.get(Calendar.YEAR).toString()

        //누르면 date_picker 뜨도록
        btn_date_picker.setOnClickListener {

            val pd = DatePickerMonth()
            pd.show(requireFragmentManager(), "datePicker")
            pd.setListener(datepicker_listener)

        }

        val cal_adapter=GraphDetailWeekCalAdapter(view.context, 5)
        graph_detail_week_cal.adapter=cal_adapter

        val weeks_adapter = GraphDetailWeekGraphAdapter(view.context)
        //onlyfortest
        val datas = createDatas()
        weeks_adapter.datas=datas
        rv_graph_weeks.adapter=weeks_adapter


        val listener = object : onMyChangeListener{
            override fun onChange(position: Int, isVisible: Boolean) {
                // 보이게 만들고 싶은 거면,
                if(isVisible){
                    val item_view = rv_graph_weeks.layoutManager?.findViewByPosition(position)
                    item_view?.visibility = View.VISIBLE
//                rv_graph_weeks.layoutManager?.findViewByPosition(position)?.layoutParams = RecyclerView.LayoutParams(0,0)
                    val params = item_view?.layoutParams
                    params?.height= RecyclerView.LayoutParams.WRAP_CONTENT
                    item_view?.layoutParams=params
                    view.invalidate()
                    weeks_adapter.notifyDataSetChanged()
                }else{
                    val item_view = rv_graph_weeks.layoutManager?.findViewByPosition(position)
                    item_view?.visibility = View.GONE
//                rv_graph_weeks.layoutManager?.findViewByPosition(position)?.layoutParams = RecyclerView.LayoutParams(0,0)
                    val params = item_view?.layoutParams
                    params?.height=0
                    item_view?.layoutParams=params


                    view.invalidate()
                    weeks_adapter.notifyDataSetChanged()

                }
            }
        }
        cal_adapter.set(listener)

        barchart_compare.drawDoubleGraph(view.context, arrayListOf(3,1,21,11,-1,4,2), arrayListOf(1,0,20,5,4,-1,2))

        // 메모 수정 버튼 초기 설정 = 안눌려있고, inactivate
        var is_btn_condition_pressed = false
        et_condition_count_noti.inactivate(view.context)
        et_condition_count_order.inactivate(view.context)

        // 메모수정 버튼 누르면 완료로 바뀌면서 edittext 색 변경
        btn_confirm_condition_change.setOnClickListener {
            //그전에 눌려있었다면,
            if(is_btn_condition_pressed){
                is_btn_condition_pressed = false
                btn_confirm_condition_change.text="메모수정"
                btn_confirm_condition_change.setTextColor(view.context.getColorFromRes(R.color.darkgrey))
                btn_confirm_condition_change.typeface = ResourcesCompat.getFont(view.context, R.font.nanum_square_bold)

                et_condition_count_noti.inactivate(view.context)
                et_condition_count_order.inactivate(view.context)

            }else{
                is_btn_condition_pressed = true
                btn_confirm_condition_change.text="완료"
                btn_confirm_condition_change.setTextColor(view.context.getColorFromRes(R.color.yellow))
                btn_confirm_condition_change.typeface = ResourcesCompat.getFont(view.context, R.font.nanum_square_extra_bold)

                et_condition_count_noti.activate(view.context)
                et_condition_count_order.activate(view.context)
            }

        }
        // 비교 함수 datepicker 설정
        val compare_cal_click_listener1 = View.OnClickListener{
            val pd_week = DatePickerWeek()
            pd_week.show(requireFragmentManager(), "datePickerMonth")
            pd_week.setListener(compare_datepicker_listener1)
        }
        val compare_cal_click_listener2 = View.OnClickListener{
            val pd_week = DatePickerWeek()
            pd_week.show(requireFragmentManager(), "datePickerMonth")
            pd_week.setListener(compare_datepicker_listener2)
        }
        tv_compare_year1.setOnClickListener(compare_cal_click_listener1)
        tv_compare_month1.setOnClickListener(compare_cal_click_listener1)
        tv_compare_week1.setOnClickListener(compare_cal_click_listener1)

        tv_compare_year2.setOnClickListener(compare_cal_click_listener2)
        tv_compare_month2.setOnClickListener(compare_cal_click_listener2)
        tv_compare_week2.setOnClickListener(compare_cal_click_listener2)

        btn_confirm_compare.setOnClickListener {
            showToast(view.context, "첫번째와 동일한 날짜를 선택하실 수 없습니다.")
        }

    }

    private fun createDatas() : MutableList<GraphSingleWeekData>{
        return mutableListOf<GraphSingleWeekData>(
            GraphSingleWeekData( "5월 1일",
            "5월 7일",
            arrayListOf(-1, 11, 0, 3, 5, -1, 0)),
            GraphSingleWeekData( "5월 8일",
                "5월 15일",
                arrayListOf(-1, 2, 0, 3, 5, 7, 0)),
            GraphSingleWeekData( "5월 15일",
                "5월 21일",
                arrayListOf(-1, 11, 2, 3, 5, -1, 0)),
            GraphSingleWeekData( "5월 22일",
                "5월 28일",
                arrayListOf(-1, 2, 0, 3, 5, -1, 0)),
            GraphSingleWeekData( "5월 29일",
                "5월 31일",
                arrayListOf(-1, 11, 0, 3, 5, -1, 0))
        )
    }

    // 기본 설정 변경에서 et 기록 수정 가능하도록
    fun EditText.activate(context: Context){
        this.background = ContextCompat.getDrawable(context, R.drawable.graph_rec9_white)
        this.isEnabled = true
    }
    fun EditText.inactivate(context: Context){
        this.background = ContextCompat.getDrawable(context, R.drawable.graph_rec9_whitegrey)
        this.isEnabled = false

    }
    // 비교 그래프에서 년, 월, 일 datepicker 로부터 입력받은 거 tv 에 입력하기
    // isFirst 는 첫번째 비교 그래프 입력인지, 두번째 비교 그래프 입력인지
    fun printDatesToCompareGraph(isFirst:Boolean, year: Int, month:Int, week : Int){
        if(isFirst){
            tv_compare_year1.text=year.toString()
            tv_compare_month1.text=if(month<10) "0"+month.toString() else month.toString()
            tv_compare_week1.text = week.toString()
        }else{
            tv_compare_year2.text = year.toString()
            tv_compare_month2.text = if(month<10) "0"+month.toString() else month.toString()
            tv_compare_week2.text = week.toString()
        }

    }

}
interface onMyChangeListener{
    // position 에 있는 item을 isvisible 하게 만들지
    fun onChange(position: Int, isVisible : Boolean)
}

fun showToast(context: Context, message : String){
    val inflater: LayoutInflater = LayoutInflater.from(context)


    val toast_view : View = inflater.inflate(R.layout.layout_custom_toast, null)

    toast_view.toast_message.text=message
    val toast= Toast(context)
    toast.view=toast_view
    toast.duration = Toast.LENGTH_SHORT
    toast.show()
    toast.setGravity(Gravity.BOTTOM,0,300)

}
