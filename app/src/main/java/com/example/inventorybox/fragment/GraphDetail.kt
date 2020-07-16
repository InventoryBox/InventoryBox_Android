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
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.etc.DatePickerMonth
import com.example.inventorybox.R
import com.example.inventorybox.adapter.GraphDetailWeekCalAdapter
import com.example.inventorybox.adapter.GraphDetailWeekGraphAdapter
import com.example.inventorybox.data.GraphInfo
import com.example.inventorybox.data.RequestGraphDetailCountEdit
import com.example.inventorybox.etc.DatePickerWeek
import com.example.inventorybox.getColorFromRes
import com.example.inventorybox.graph.drawDoubleGraph
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.fragment_graph_detail.*
import kotlinx.android.synthetic.main.fragment_graph_detail.cal_month
import kotlinx.android.synthetic.main.layout_custom_toast.view.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GraphDetail : Fragment() {

    var datas = ArrayList<GraphInfo>()
    var item_idx = -1
    var count_noti = 0
    // 발주 수량 메모
    var count_order = -1
    var max_week = 5
    var product_name : String = ""
    lateinit var cal_adapter: GraphDetailWeekCalAdapter
    lateinit var weeks_adapter : GraphDetailWeekGraphAdapter
    lateinit var mycontext : Context
    lateinit var cal_click_listener : onMyChangeListener

    var data_week1 = arrayListOf<Int>(-1,-1,-1,-1,-1,-1,-1)
    var data_week2 = arrayListOf<Int>(-1,-1,-1,-1,-1,-1,-1)

    // double 그래프 첫번째 입력됐는지
    var hasFirstData = false
    var hasSecondData = false
    
    //date picker 에서 받은 이벤트를 본 fragment 에 전달해주는 listener
    val datepicker_listener: DatePickerDialog.OnDateSetListener = object  : DatePickerDialog.OnDateSetListener{
        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, p3: Int) {
//            Log.d("datepicker","year = $year, month = $month")
            cal_month.text=if(month<10) "0"+month.toString() else month.toString()
            cal_year.text=year.toString()
            requestData(year, month)
            graph_detail_week_cal.adapter = cal_adapter
            rv_graph_weeks.adapter = weeks_adapter

        }
    }

    val compare_datepicker_listener1 = object : DatePickerDialog.OnDateSetListener{
        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, week: Int) {
//            Log.d("datepicker","year = $year, month = $month, week = $week")
            printDatesToCompareGraph(true, year, month, week)
            getDoubleData()
        }
    }
    val compare_datepicker_listener2 = object : DatePickerDialog.OnDateSetListener{
        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, week: Int) {
//            Log.d("datepicker","year = $year, month = $month, week = $week")
            printDatesToCompareGraph(false, year, month, week)
            getDoubleData()
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

        mycontext = view.context
        // 상단 캘린더 설정
        val cal : Calendar = Calendar.getInstance()

        val format = SimpleDateFormat("MM")

        cal_month.text=format.format(cal.time)
        cal_year.text=cal.get(Calendar.YEAR).toString()
        graph_detail_week_cal.isNestedScrollingEnabled=false

        cal_adapter=GraphDetailWeekCalAdapter(view.context, max_week)
        graph_detail_week_cal.adapter=cal_adapter

        weeks_adapter = GraphDetailWeekGraphAdapter(view.context)
        weeks_adapter.datas=datas
        weeks_adapter.count_noti = count_noti
        rv_graph_weeks.adapter=weeks_adapter

        // 오늘 날짜로 데이터 가져오기
        requestData(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1)


        //product name 설정
        tv_product_name.text=product_name

        // 뒤로가기 버튼
        btn_back.setOnClickListener {
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.remove(this).commit()
        }



        //누르면 date_picker 뜨도록
        btn_date_picker.setOnClickListener {

            val pd = DatePickerMonth()
            pd.show(requireFragmentManager(), "datePicker")
            pd.setListener(datepicker_listener)

        }



        cal_click_listener = object : onMyChangeListener{
            override fun onChange(position: Int, isVisible: Boolean) {
                // 보이게 만들고 싶은 거면,
                if(isVisible){
                    val item_view = rv_graph_weeks.layoutManager?.findViewByPosition(position)
                    if(position==max_week){
                        item_view?.layoutParams?.height =RecyclerView.LayoutParams.WRAP_CONTENT
                        item_view?.visibility = View.VISIBLE
                        view.invalidate()
                        weeks_adapter.notifyDataSetChanged()
                    }else{

                        item_view?.visibility = View.VISIBLE
//                rv_graph_weeks.layoutManager?.findViewByPosition(position)?.layoutParams = RecyclerView.LayoutParams(0,0)
                        val params = item_view?.layoutParams
                        params?.height= RecyclerView.LayoutParams.WRAP_CONTENT
                        item_view?.layoutParams=params
                        view.invalidate()
                        weeks_adapter.notifyDataSetChanged()
//                        Log.d("testtest","$position view visible")
                    }
                }else{
                    val item_view = rv_graph_weeks.layoutManager?.findViewByPosition(position)


                    if(position==max_week-1){
//                        item_view?.visibility = View.INVISIBLE
                        item_view?.visibility = View.INVISIBLE
                        item_view?.layoutParams?.height = 10
//                rv_graph_weeks.layoutManager?.findViewByPosition(position)?.layoutParams = RecyclerView.LayoutParams(0,0)
//                        val params = item_view?.layoutParams
//                        params?.height=R.dimen.detail_graph_height
//                        item_view?.layoutParams=params
                        view.invalidate()
                        weeks_adapter.notifyDataSetChanged()
                    }else{
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
        }
        cal_adapter.set(cal_click_listener)

        barchart_compare.drawDoubleGraph(view.context, data_week1,data_week2)


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
                postEditedCount()
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
            // 모두 입력돼있지 않으면 toast
//

//            barchart_compare.invalidate()
//            barchart_compare.drawDoubleGraph(view.context, arrayListOf(3,1,21,11,-1,4,2), arrayListOf(1,0,20,5,4,-1,2))
            try{
//                getDoubleData(
//                    arrayListOf(Integer.parseInt(tv_compare_year1.text.toString()), Integer.parseInt(tv_compare_month1.text.toString()), Integer.parseInt(tv_compare_week1.text.toString())),
//                    arrayListOf(Integer.parseInt(tv_compare_year2.text.toString()), Integer.parseInt(tv_compare_month2.text.toString()), Integer.parseInt(tv_compare_week2.text.toString()))
//                )
//                barchart_compare.clear()
//                data_week1 = arrayListOf(1,1,1,1,3,1,2)
//                data_week2 = arrayListOf(2,1,3,3,3,1,2)
                barchart_compare.drawDoubleGraph(view.context, data_week1, data_week2)
                barchart_compare.notifyDataSetChanged()
                barchart_compare.invalidate()
            }catch (e : Exception){
                showToast(view.context, "날짜를 다시 선택해주세요")
            }
            Log.d("graphdetail", data_week1.toString()+data_week2.toString())

        }

    }

    //서버에 데이터 요청
    fun requestData(year : Int, month : Int) {

        //item idx 값 검색
        val bundle = this.arguments
        item_idx = bundle!!.getInt("itemIdx",0)
        product_name = bundle!!.getString("item_name").toString()
        datas = arrayListOf()
        RequestToServer.service.requestGraphDetailData(
            item_idx!!,
            getString(R.string.test_token),
            year,
            month
        ).customEnqueue(
            onSuccess = {
//                count_noti = it.data.alarmCnt
                max_week = it.data.weeksCnt
                count_noti = it.data.alarmCnt
                count_order = it.data.memoCnt
                et_condition_count_noti.setText(count_noti.toString())
                et_condition_count_order.setText(count_order.toString())
                for(info in it.data.graphInfo){
                    datas.add(info)
                }

                weeks_adapter.datas = datas
                weeks_adapter.count_noti = it.data.alarmCnt
                weeks_adapter.notifyDataSetChanged()
                this.view?.invalidate()
            }
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
    // 완료 누르면 기본 설정 변경 서버에 전달



    // 비교 그래프에서 년, 월, 일 datepicker 로부터 입력받은 거 tv 에 입력하기
    // isFirst 는 첫번째 비교 그래프 입력인지, 두번째 비교 그래프 입력인지
    fun printDatesToCompareGraph(isFirst:Boolean, year: Int, month:Int, week : Int){
        if(isFirst){
            tv_compare_year1.text=year.toString()
            tv_compare_month1.text=if(month<10) "0"+month.toString() else month.toString()
            tv_compare_week1.text = week.toString()
            hasFirstData = true
        }else{
            tv_compare_year2.text = year.toString()
            tv_compare_month2.text = if(month<10) "0"+month.toString() else month.toString()
            tv_compare_week2.text = week.toString()
            hasSecondData = true
        }
    }

    fun postEditedCount(){
        RequestToServer.service.requestGraphDetailCountEdit(
            getString(R.string.test_token),
            item_idx,
            RequestGraphDetailCountEdit(
                Integer.parseInt(et_condition_count_noti.text.toString()),
                Integer.parseInt(et_condition_count_order.text.toString())
            )).customEnqueue(
            onSuccess = {
                Log.d("GraphDetail", it.message + "success "+it.success)
            }
        )
    }

    fun getDoubleData(){

        try{
            arrayListOf(Integer.parseInt(tv_compare_year1.text.toString()), Integer.parseInt(tv_compare_month1.text.toString()), Integer.parseInt(tv_compare_week1.text.toString()))
            arrayListOf(Integer.parseInt(tv_compare_year2.text.toString()), Integer.parseInt(tv_compare_month2.text.toString()), Integer.parseInt(tv_compare_week2.text.toString()))
            val year1 = tv_compare_year1.text.toString()
            val month1 = tv_compare_month1.text.toString()
            val week1 = tv_compare_week1.text.toString()
            val year2 = tv_compare_year2.text.toString()
            val month2 = tv_compare_month2.text.toString()
            val week2 = tv_compare_week2.text.toString()

            RequestToServer.service.requestGraphDetailComparativeData(
                item_idx,
                getString(R.string.test_token),
                "$year1,$month1,$week1",
                "$year2,$month2,$week2"
            ).customEnqueue(
                onSuccess = {
                    data_week1 = arrayListOf()
                    data_week2 = arrayListOf()
                    Log.d("graphdetail", it.message)
                    if(it.data.week1.isNotEmpty()){
//                    barchart_compare.drawDoubleGraph(mycontext, it.data.week1, it.data.week2)
//                    this.week1 = it.data.week1
//                    this.week2 = it.data.week2
                        for(data in it.data.week1){
                            data_week1.add(data)
                        }
                        for(data in it.data.week2){
                            data_week2.add(data)
                        }
//                    data_week1 = arrayListOf(1,1,1,1,1,1,1)
//                    data_week2= arrayListOf(1,1,1,1,1,0,2)
//                    barchart_compare.drawDoubleGraph(mycontext, data_week1, data_week2)
                        Log.d("graphdetail",it.data.week1.toString())
//                    Log.d("graphdetail", it.data.week2.toString())
                    }
                }
            )
        }catch (e : Exception){

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
