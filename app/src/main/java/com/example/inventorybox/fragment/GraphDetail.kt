package com.example.inventorybox.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.LinearLayout
import androidx.core.view.marginBottom
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.FixAppBarLayoutBehavior
import com.example.inventorybox.etc.DatePickerWeekOnly
import com.example.inventorybox.R
import com.example.inventorybox.adapter.GraphDetailWeekCalAdapter
import com.example.inventorybox.adapter.GraphDetailWeekGraphAdapter
import com.example.inventorybox.data.GraphSingleWeekData
import com.example.inventorybox.graph.drawDoubleGraph
import kotlinx.android.synthetic.main.fragment_graph.*
import kotlinx.android.synthetic.main.fragment_graph_detail.*
import kotlinx.android.synthetic.main.fragment_graph_detail.cal_month
import java.text.SimpleDateFormat
import java.util.*

class GraphDetail : Fragment() {



    val listener: DatePickerDialog.OnDateSetListener = object  : DatePickerDialog.OnDateSetListener{
        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, p3: Int) {
            Log.d("datepicker","year = $year, month = $month")
            cal_month.text=if(month<10) "0"+month.toString() else month.toString()
            cal_year.text=year.toString()
        }
    }
//    val month_listener: OnValueChangeListener = object : OnValueChangeListener{
//        override fun onValueChange(picker: WheelPicker, oldVal: String, newVal: String) {
//            cur_month=newVal
//        }
//    }
//    val year_listener :OnValueChangeListener =object : OnValueChangeListener{
//        override fun onValueChange(picker: WheelPicker, oldVal: String, newVal: String) {
//            cur_year=newVal
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_graph_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

            val pd = DatePickerWeekOnly()
            pd.show(requireFragmentManager(), "datePicker")
            pd.setListener(listener)

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
                    params?.height= LinearLayout.LayoutParams.WRAP_CONTENT
                    item_view?.layoutParams=params
                    view.invalidate()
                    weeks_adapter.notifyDataSetChanged()
                    weeks_adapter.notifyItemChanged(position)
                }else{
                    val item_view = rv_graph_weeks.layoutManager?.findViewByPosition(position)
                    item_view?.visibility = View.GONE
//                rv_graph_weeks.layoutManager?.findViewByPosition(position)?.layoutParams = RecyclerView.LayoutParams(0,0)
                    val params = item_view?.layoutParams
                    params?.height=0
                    item_view?.layoutParams=params


                    view.invalidate()
                    weeks_adapter.notifyDataSetChanged()
                    weeks_adapter.notifyItemChanged(position)
                }
            }
        }
        cal_adapter.set(listener)

        barchart_compare.drawDoubleGraph(view.context, arrayListOf(3,1,2,0,-1,4,2), arrayListOf(1,0,2,5,4,-1,2))


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


}
interface onMyChangeListener{
    // position 에 있는 item을 isvisible 하게 만들지
    fun onChange(position: Int, isVisible : Boolean)
}