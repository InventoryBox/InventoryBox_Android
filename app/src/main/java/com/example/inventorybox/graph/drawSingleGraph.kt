package com.example.inventorybox.graph

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.inventorybox.R
import com.example.inventorybox.getColorFromRes
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet

// 일요일에서 월요일까지의 데이터를 ArrayList로 전달받아 그래프를 그려주는 함수

fun BarChart.drawSingleGraph(context: Context, datas : ArrayList<Int>, count_noti:Int) {

    var data : BarData = createChartData(context, datas)
    configureChartAppearance( this)
    prepareChartData(context, this, data)
    //bar 위에 value 위치하도
    this.setDrawValueAboveBar(true)

    //알림 개수 라인 그리기
    drawAxisLine(context, this, count_noti)
}

private fun drawAxisLine(context: Context, barchart : BarChart, num : Int) {
    val line :LimitLine = LimitLine(num.toFloat())
    barchart.axisLeft.addLimitLine(line)
    line.lineColor= context.getColorFromRes(R.color.yellow)

}

// 데이터 받아서
private fun prepareChartData(context: Context, barchart : BarChart,data: BarData) {
    //value text size 설정
    data.setValueTextSize(12f)
    // text color 설
    data.setValueTextColor(context.getColorFromRes(R.color.darkgrey))
    barchart.data=data
    barchart.invalidate()
}

// BarData만들기
private fun createChartData(context: Context, datas :ArrayList<Int>): BarData {
    val values: ArrayList<BarEntry> = ArrayList()

    for (i in 0..6){
        values.add(BarEntry(i.toFloat(), datas.get(i).toFloat()))
    }


//        val set = BarDataSet(values, SET_LABEL)
    //바 색 바꾸기
    //set.color= ContextCompat.getColor(this, R.color.yellow)

    val set = CustomBarDataSet(values, "SET_LABEL")
    /*set.valueFormatter=object: ValueFormatter(){
        override fun getFormattedValue(value: Float): String {
            Log.d("datete"," "+value.toInt()+" ")
            return value.toInt().toString()
        }
    }*/
//        set.barBorderWidth=10f
//        set.barBorderColor=getColorFromRes(R.color.yellow)
    set.colors=
            //listOf(ContextCompat.getColor(this,R.color.yellow),ContextCompat.getColor(this, R.color.gray))
        listOf(context.getColorFromRes(R.color.yellow), context.getColorFromRes(R.color.middlegrey))
    val dataSets = ArrayList<IBarDataSet>()
    dataSets.add(set)



    val data:BarData = BarData(dataSets)
    //value값을 int
    data.setValueFormatter(object: ValueFormatter(){
        override fun getFormattedValue(value: Float): String {
//                Log.d("datete"," "+value.toInt()+" ")
            return Math.round(value).toString()
        }
    })

    //막대 너비 수정
    data.barWidth=0.2f

    return data
}

//chart 가 어떻가 보여질
private fun configureChartAppearance(barchart : BarChart) {


    val DAYS = arrayListOf<String>("일","월","화","수","목","금","")

    barchart.description.isEnabled=false
    barchart.setDrawValueAboveBar(false)

    //legend없애기
    barchart.legend.isEnabled=false

    val renderer=RoundedChartRenderer(barchart, barchart.animator, barchart.viewPortHandler)

    renderer.setmRadius(30f)
    barchart.renderer = renderer





    val x_axis = barchart.xAxis
//        x_axis.position=
//        //x 축 color 설정
//        x_axis.textColor=getColorFromRes(R.color.gray)
    //x축 bottom에 위치
    x_axis.position=XAxis.XAxisPosition.BOTTOM
    //x축에 요일 입력
    x_axis.setDrawGridLines(false)
    x_axis.valueFormatter= object : ValueFormatter(){
        override fun getFormattedValue(value:Float): String {
            return DAYS.get(value.toInt())
        }
    }

    //y축의 활성화 없애개
    val axisLeft = barchart.axisLeft
    axisLeft.granularity=10f
    axisLeft.axisMinimum= 0f
    axisLeft.setDrawAxisLine(false)
    axisLeft.setDrawLabels(false)

    /*axisLeft.valueFormatter=object: ValueFormatter(){
        override fun getFormattedValue(value: Float): String {
            Log.d("datete"," "+value.toInt()+" ")
            return value.toInt().toString()
        }
    }*/

    val axisRight = barchart.axisRight

//       axisRight.granularity=20f
//        axisRight.setDrawLabels(false)
//        axisRight.axisMinimum=0f

    axisRight.isEnabled=false


}
//
//    var data : BarData = createChartData(context, this, datas)
//    configureChartAppearance(this)
//
//    data.barWidth=0.2f
//    drawAxisLine(context,this,  count_noti)
//}
//
//// 발주 알림개수 line그리는 함수
//fun drawAxisLine(context: Context,barchart:BarChart, count_noti: Int) {
//    val line : LimitLine = LimitLine(count_noti.toFloat())
//    barchart.axisLeft.addLimitLine(line)
//    line.lineColor= context.getColorFromRes(R.color.yellow)
//}
//
//fun createChartData(context: Context, barChart: BarChart, datas: ArrayList<Int>): BarData {
//    val values: ArrayList<BarEntry> = ArrayList()
//
//    for (i in 0..6) {
//        values.add(BarEntry(i.toFloat(), datas.get(i).toFloat()))
//    }
//    val set = CustomBarDataSet(values, "")
//    set.colors =
//        listOf(context.getColorFromRes(R.color.yellow), context.getColorFromRes(R.color.middlegrey))
//    val data_set = ArrayList<IBarDataSet>()
//    data_set.add(set)
//
//
//    val data: BarData = BarData(data_set)
//    //value값을 int로 바꾸고, 값이 없으면(-1) 0으로
//    data.setValueFormatter(object : ValueFormatter() {
//        override fun getFormattedValue(value: Float): String {
//            return if (value >= 0) Math.round(value).toString() else ""
//        }
//    })
//    return data
//}
//
//// 차트가 전반적으로 어떻게 보여질 지를 결정
//fun configureChartAppearance(barchart: BarChart) {
//    val DAYS = arrayListOf<String>("일","월","화","수","목","금","토")
//
//    barchart.description.isEnabled=false
//    barchart.legend.isEnabled=false
//
//    // bar를 둥근 모양으로
//    val renderer = RoundedChartRenderer(barchart, barchart.animator, barchart.viewPortHandler)
//    renderer.setmRadius(30f)
//    barchart.renderer=renderer
//
//    //x축 결정
//    val x_axis = barchart.xAxis
//    x_axis.position=XAxis.XAxisPosition.BOTTOM
//    x_axis.setDrawGridLines(false)
//    x_axis.valueFormatter= object : ValueFormatter(){
//        override fun getFormattedValue(value:Float): String {
//            return DAYS.get(value.toInt())
//        }
//    }
//
//    //y축 결정
//    val axisLeft = barchart.axisLeft
//    axisLeft.granularity=10f
//    axisLeft.axisMinimum= 0f
//    axisLeft.setDrawAxisLine(false)
//    axisLeft.setDrawLabels(false)
//
//    barchart.axisRight.isEnabled=false
//
//}
