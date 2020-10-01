package com.inventorybox.inventorybox.graph

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import com.inventorybox.inventorybox.R
import com.inventorybox.inventorybox.getColorFromRes
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet


// datas : 최근 5일 재고량 int arraylist
// day : 마지막 재고량의 요일 - 일요일(0) ~ 토요일(6)
fun BarChart.draw5DaysGraph(context: Context, datas : ArrayList<Int>, day : Int, count_noti:Int) {

    this.setTouchEnabled(false)
    datas.add(0,-1)
    datas.add(0,-1)

    var data : BarData = createChartData(context, datas,count_noti)
    configureChartAppearance( this,context, day)
    prepareChartData(context, this, data)

    //bar 위에 value 위치하도
    this.setDrawValueAboveBar(true)
    //알림 개수 라인 그리기
    if(count_noti != -1){
        drawAxisLine(context, this, count_noti)
    }
}

// num에 해당하는 value의 수평선 그린
private fun drawAxisLine(context: Context, barchart : BarChart, num : Int) {
    val line : LimitLine = LimitLine(num.toFloat(), "발주 알림 개수 $num")
    barchart.axisLeft.addLimitLine(line)
    line.lineColor= context.getColorFromRes(R.color.yellow)
    line.labelPosition = LimitLine.LimitLabelPosition.LEFT_TOP
    line.lineWidth=1f
    line.textColor = context.getColorFromRes(R.color.yellow)
    line.typeface = ResourcesCompat.getFont(context, R.font.nanum_square_extra_bold )
    line.textSize = 12f
    // y 축으로부터 거리 설정
    line.yOffset=3f
//    line.xOffset=-10f
//    barchart.animateX(2000)
//    barchart.animateY(2000)

}

// 데이터 받아서다
private fun prepareChartData(context: Context, barchart : BarChart, data: BarData) {
    //value text size 설정
    data.setValueTextSize(12f)
    // text color 설
    data.setValueTextColor(context.getColorFromRes(R.color.darkgrey))
    barchart.data=data
    barchart.invalidate()
}

// BarData만들기
private fun createChartData(context: Context, datas :ArrayList<Int>, count_noti: Int): BarData {
    val values: ArrayList<BarEntry> = ArrayList()

    for (i in 0..6){
        values.add(BarEntry(i.toFloat(), datas.get(i).toFloat()))
    }

    val set = CustomBarDataSet(values, "SET_LABEL",count_noti)
    set.colors=
            //listOf(ContextCompat.getColor(this,R.color.yellow),ContextCompat.getColor(this, R.color.gray))
        listOf(context.getColorFromRes(R.color.yellow), context.getColorFromRes(R.color.middlegrey))
    val dataSets = ArrayList<IBarDataSet>()
    dataSets.add(set)



    val data: BarData = BarData(dataSets)
    //value값을 int로
    data.setValueFormatter(object: ValueFormatter(){
        override fun getFormattedValue(value: Float): String {
            return if(value>=0) Math.round(value).toString() else "".toString()
        }
    })
    data.setValueTypeface(ResourcesCompat.getFont(context, R.font.nanum_square_extra_bold))

    //막대 너비 수정
    data.barWidth=0.2f

    return data
}

//chart 가 어떻가 보여질지
//day 는 마지막 데이터의 요일
private fun configureChartAppearance(barchart : BarChart, context: Context, day: Int) {


    val DAYS = arrayListOf<String>("일","월","화","수","목","금","토","일","월","화","수","목","금","토")
    val first_day  = if(day-4>=0) day-4 else day+3


//    Log.d("testtest","firstday = $first_day")
//    val day5 = arrayListOf<String>("","","일","월","화","수","목")
    val day5 = arrayListOf<String>("","")

    for(i in first_day..(first_day+4)){
        day5.add(DAYS.get(i))
    }
    for(i in day5){
//        Log.d("testtest","$i")
    }

    barchart.description.isEnabled=false
    barchart.setDrawValueAboveBar(false)

    //legend없애기
    barchart.legend.isEnabled=false

    val renderer=RoundedChartRenderer(barchart, barchart.animator, barchart.viewPortHandler)

    renderer.setmRadius(30f)
    barchart.renderer = renderer



    val x_axis = barchart.xAxis

    //x축 bottom에 위치
    x_axis.position= XAxis.XAxisPosition.BOTTOM
    //x축에 요일 입력
    x_axis.granularity=1f
    x_axis.setDrawGridLines(false)
    x_axis.valueFormatter= object : ValueFormatter(){
        override fun getFormattedValue(value:Float): String {
            return day5.get(value.toInt())
//            return value.toString()
        }
    }
    x_axis.typeface= ResourcesCompat.getFont(context, R.font.nanum_square_bold )
    x_axis.textSize=11f
//    x_axis.spaceMin = 5f

    //y축의 활성화 없애개
    val axisLeft = barchart.axisLeft
    axisLeft.granularity=1f
    axisLeft.axisMinimum= 0f
    axisLeft.labelCount=5
    axisLeft.setDrawAxisLine(false)
    axisLeft.setDrawLabels(false)
    axisLeft.setDrawGridLines(false)

    val axisRight = barchart.axisRight



    axisRight.isEnabled=false


}
