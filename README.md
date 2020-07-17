
# InventoryBox_Android
🦖android github🦖 https://github.com/InventoryBox/InventoryBox_Android
<br>


# 재고창고
<img style="border: 1px solid black !important; border-radius:20px; " src="https://user-images.githubusercontent.com/63707317/86824314-f1be6380-c0c8-11ea-8893-e5856316f338.png" width="200px" />

###  개인 외식 사업자를 위한 재고관리 Application 📦
<b>'나만의 다이어리를 관리하듯 매일매일 쉽게 기록하고 성장할 수 있는 재고관리 플랫폼', 재고창고입니다. </b><br/>
발주시점을 놓치지 않게 도와주는 발주 알림 기능, 데이터를 쉽게 축적할 수 있는 재고 기록 기능이 있습니다. 
또한，재고교환 기능을 통해 재고가 떨어지는 갑작스러운 상황에도 개인 사업자들간 네트워크 형성을 통해 재고를 보충할 수 있습니다.
 <br>

## Project
* SOPT 26th APPJAM 
* 프로젝트 기간: 2020.06.13 ~ 2020.07.18

<img style="border: 1px solid black !important; border-radius:20px; " src="https://user-images.githubusercontent.com/63707317/86822421-92f7ea80-c0c6-11ea-965f-0d14951ce44e.png" width="600px" />
<br>

## 개발 환경
##### * Android Studio, kotlin
<br>

## 사용 라이브러리  
```kotlin
    //Retrofit 라이브러리
    implementation 'com.squareup.retrofit2:retrofit:2.6.2'
    //Retrofit 라이브러리 응답으로 가짜 객체를 만들기 위해
    implementation 'com.squareup.retrofit2:retrofit-mock:2.6.2'

    //객체 시리얼라이즈를 위한 Gson 라이브러리
    implementation 'com.google.code.gson:gson:2.8.6'
    //Retrofit 에서 Gson 을 사용하기 위한 라이브러리
    implementation 'com.squareup.retrofit2:converter-gson:2.6.2'

    //배경이 동그란 이미지뷰를 사용할 수 있도록 해주는 라이브러리
    implementation 'de.hdodenhof:circleimageview:3.1.0'

    //stickyscrollview 라이브러리
    implementation 'com.github.didikk:sticky-nestedscrollview:1.0.1'

    //expandable layout
    implementation 'net.cachapa.expandablelayout:expandablelayout:2.9.2'

    // mpandroidchart 막대 차트
    implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'

    //wheel picker datepicker
    implementation 'com.super_rabbit.wheel_picker:NumberPicker:1.0.1'

    //kakao 지도 검색 api 이용을 위한 모듈
    implementation "com.kakao.sdk:v2-user-rx:2.0.0-beta02"
    implementation "com.kakao.sdk:v2-talk-rx:2.0.0-beta02"
    implementation "com.kakao.sdk:v2-story-rx:2.0.0-beta02"
    implementation "com.kakao.sdk:v2-link-rx:2.0.0-beta02"
    implementation "com.kakao.sdk:v2-navi:2.0.0-beta02"

```

<br>
    
## 기능 소개
| 기능 | 상세 기능 | 담당 |
 |:--------|:--------|:--------:| 
 | 스플레시 | 스플레시 화면 | 전성은 | 
 | 로그인 | 로그인 | 전성은 | 
 | 회원가입 | 이메일 인증 | 전성은 |
 | 홈 | 재고 목록 | 전성은 | 
 |  | 발주 확인 | 전성은 |
 |  | 체크박스 | 김가영 |
 |  | 더보기 | 전성은 |
 |  | 메모수정 | 전성은 |
 |  | Side Menu View | 전성은 |
 | 재고기록 | 캘린더 | 정화진 |
 |  | Date pickerView Custom| 김가영 |
 |  | 카테고리 분류 | 정화진 |
 |  | 카테고리 필터 | 정화진 |
 |  | 카테고리 수정 | 정화진 |
 |  | 카테고리 추가 | 정화진 |
 |  | 재료 추가 | 정화진 |
 |  | 재료 목록 | 정화진 |
 |  | 카테고리 분류 | 정화진 |
 |  | Check Box | 정화진 |
 |  | Alert Custom | 정화진 |
 | 재고량 추이 | 캘린더 day 분류 | 김가영 |
 | | 캘린더 Week 분류 | 김가영 |
 | | 카테고리 선택 | 김가영 |
 | | 카테고리 필터 | 김가영 |
 | | Graph | 김가영 |
 | | 캘린더 | 김가영 |
 | 재고교환 | 가게 위치 설정 | 김가영 |
 | | 카테고리 | 김가영 |
 | | 게시글 정렬 | 김가영 |
 | | 게시글 상세 | 김가영 |
 | | 게시글 등록 | 김가영 |
 <br>

## 프로그램 구조
#### package 분류

<div>
<img src="https://user-images.githubusercontent.com/51014789/87791076-a5b7af80-c87c-11ea-98c7-13bba6b49cf3.PNG" width="20%">
</div>
프로그램 구조는 크게 activity, adapter, data, db, fragment, graph, network, viewholder, etc 로 분류했다. network에는 싱글톤인 RequestToServer, interface인 NetworkService, kotlin 확장함수를 이용한 customEnqueue가 있으며 GET, POST, PUT으로 나누어 분류했다. graph 패키지는 다양한 종류의 그래프를 위한 확장함수들로 구성되어 있다. db 패키지에는 유저 정보 저장을 위한 SharedPreferenceController가 들어있다. etc 패키지에는 datepicker, recyclerview decoration 등 다양한 custom을 위한 클래스와 확장 함수들이 있다. <br> 
 
<br>

## 핵심 기능 구현 방법 및 구현 화면

#### <홈>

체크박스 - onHomeCheckLister를 이용하여 체크박스와 체크리스트를 연결 <br>
'자세히' 버튼을 눌러 최근 5일의 재고 추이를 확인 - expandable list view 와 MPAndroidChart 이용 <br>

<br>
<div>
<img src="https://user-images.githubusercontent.com/51014789/87784667-b1ea3f80-c871-11ea-9929-6779e887754f.PNG" width="23%">
<img src="https://user-images.githubusercontent.com/51014789/87784859-0a214180-c872-11ea-9038-f6e7d92dc088.PNG" width="23%">
<img src="https://user-images.githubusercontent.com/51014789/87784680-b6165d00-c871-11ea-8438-c415838bf7fe.PNG" width="23%">
</div>
<br>  
  
#### <재고 기록>

WheelPicker 라이브러리를 이용하여 커스텀 데이트 피커 제작<br>


<div>
<img src = "https://user-images.githubusercontent.com/61824695/87776889-76487900-c863-11ea-8322-7b21cb50cd7a.png" width="23%">
<img src = "https://user-images.githubusercontent.com/61824695/87776955-9415de00-c863-11ea-84be-02152dbf068f.png" width = "23%">
<img src = "https://user-images.githubusercontent.com/61824695/87776699-2073d100-c863-11ea-822c-28ece52d3e8d.png" width = "23%">
</div>
<br>

#### <재고량 추이>

MPAndroidChart 이용하여 주간 그래프 구현<br>
MPAndroidChart 이용하여 비교 그래프 구현<br>
<div>
<img src="https://user-images.githubusercontent.com/61824695/87776524-e0ace980-c862-11ea-85f6-0993f9dec3c5.png" width="23%">
<img src="https://user-images.githubusercontent.com/61824695/87776606-00dca880-c863-11ea-8f72-a2e56ba741b1.png width="23%">
</div>
<br>

#### <재고 교환>

Kakao API 이용, 주소 검색 <br>

<div>
<img src="https://user-images.githubusercontent.com/60654009/87759503-5f952880-c849-11ea-8cc0-68f2eef84779.png" width="23%">
</div>
<br>
 
## A-1 ConstraintLayout을 사용한 화면 개발
### 1. match_constraint, chain, guideline 등 constraintLayout의 다양한 속성 활용

레이아웃을 짤 때 margin으로 여백을 주기보다는 constraintLayout의 guideline 속성을 이용하여 뷰들을 guideline에 맞추었다. <br>
ex)
<br>
<div>
 <img src="https://user-images.githubusercontent.com/51014789/87706249-24a7dc00-c7da-11ea-99aa-e57e6a2aca6a.PNG" width="23%">
 <img src="https://user-images.githubusercontent.com/51014789/87706625-c7605a80-c7da-11ea-99c6-d81337661169.PNG" width="23%">
 <img src="https://user-images.githubusercontent.com/51014789/86891495-e8211400-c139-11ea-9a06-05d28b1a8aa5.PNG" width="22%">
 <img src="https://user-images.githubusercontent.com/60654009/86902091-73090b00-c148-11ea-882e-32e91df68466.png" width="23%">
</div>
<br>
 
* activity_login.xml에서 guideline과 match_constraint 이용
guideline을 이용하여 양쪽 여백을 맞추고 뷰들의 width를 match_constraint로 하여 guideline에 꽉 차게 지정했다.

* activity_sign_up.xml에서 match_constraint, chain, guideline 이용
guideline을 이용하여 양쪽 여백을 맞추고 뷰들의 width를 match_constraint로 하여 guideline에 꽉 차게 지정했고, chain을 이용하여 각 뷰들을 연결했다.

* activity_drawer.xml에서 chain 속성 활용
각 항목들을 프로필 constraintlayout과 chain으로 연결하고 Vertical chainStyle을 packed로 지정하여 붙였다.

* activity_drawer.xml에서 match_constraint 속성 활용
레이아웃에 각 메뉴들을 꽉 차게 맞추기 위해 모든 메뉴들의 layut_width에 0dp로 match_constraint 속성을 적용했다.

* activity_add.xml에서 guidline 속성 활용

* fragment_graph_detail.xml 에서 guideline 속성, match_constraint 사용

* fragment_graph_detail.xml 에서 guideline 속성 활용
왼쪽에 같은 margin 값을 주기 위해 guideline을 만든 후 constraint 적용
<br>

### 2. 제약조건의 연관성
뷰를 부모와 연관지어 여백을 적용하는 방식이 아니라 가까운 뷰에게 제약조건을 걸어 여백을 통해 위치를 지정했다.

<br>

### 3. width, height 속성에 match_parent, wrap_content, match_constraint 위주로 사용

* textView의 text 내용에 따라 크기가 달라져야 하는 경우가 많기 때문에 width 속성에 wrap_content 속성 위주로 사용
ex) 사용자 이름, 주소, 날짜, 발주 확인 목록 등
* activity_drawer.xml에 match_constraint를 활용하여 레이아웃에 각 메뉴들을 꽉 차게 지정
* 회원가입, 로그인 뷰에서 match_constraint를 활용하여 guideline에 각 editText 뷰들을 꽉 차게 지정

<br>

## A-2 kotlin collection의 확장함수 사용 / custom 확장 함수 사용

### kotlin collection의 확장함수 사용
#### map

GraphFragment 에서 category에 해당하는 값들을 새로운 arrayList에 넣어 adapter 에 반영해주기
```kotlin
if(category_idx>1){  
  sorted_datas_graph = datas_graph.filter {  
  it.categoryIdx==category_idx  
    }.toMutableList()
graph_adapter.datas = sorted_datas_graph
graph_adapter.notifyDataSetChanged()
```

ExchangeFoodFragment, ExchangeProductFragment 에서 해당하는 data 들만 넣어주기
```kotlin
for(data in it.data.postInfo){  
  datas.add(data)  
}  
val sorted : MutableList<PostInfo> = datas.filter {data->  
  data.isFood==1  
}.toMutableList()  
exchangeRVAdapter.datas=sorted  
exchangeRVAdapter.notifyDataSetChanged()
' ' 



### custom 확장 함수 사용
#### customEnqueue

kotlin extension을 이용한 메소드를 적용하였다. 통신 부분마다 customEnqueue 함수를 이용하여 반복되는 요소들을 줄일 수 있었다.

```kotlin
fun<ResponseType> Call<ResponseType>.customEnqueue(
    onFail:()-> Unit={ Log.d("network", "통신 실패")},
    onSuccess:(ResponseType)->Unit,
    onError:()->Unit={}
){
    this.enqueue(object: Callback<ResponseType> {
        override fun onFailure(call: Call<ResponseType>, t: Throwable){
            onFail()
            Log.d("network", t.message)
        }

        override fun onResponse(call: Call<ResponseType>, response: Response<ResponseType>){
            response.body()?.let{
                onSuccess(it)
            }?:onError()
            Log.d("network", response.message())
            Log.d("network", response.code().toString())
        }
    })
}
```

#### getColorFromRes
```kotlin
// color res id로부터 color 값 반환
fun Context.getColorFromRes(color:Int):Int{
    return ContextCompat.getColor(this, color)
}
```

#### draw5DaysGraph
```kotlin
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
```

#### drawDoubleGraph
```kotlin
fun BarChart.drawDoubleGraph(context:Context, data1: ArrayList<Int>,data2 : ArrayList<Int>){

    this.setTouchEnabled(false)

    var values1 = ArrayList<BarEntry>()
    var values2 = ArrayList<BarEntry>()


    Log.d("drawdoublegraph",""+ data1.toString()+data2.toString())
    // data 만들기
    for(i in 0..6){
        Log.d("drawdoublegraph","a"+ data1[i] + data2[i])
        values1.add(BarEntry(i.toFloat(), data1[i].toFloat()))
        values2.add(BarEntry(i.toFloat(), data2[i].toFloat()))
    }

    val data_set1 =BarDataSet(values1,"")
    val data_set2 =BarDataSet(values2, "")

    data_set1.color= getColorFromRes(context, R.color.middlegrey)
    data_set2.color= getColorFromRes(context, R.color.yellow)


    val data_sets = ArrayList<IBarDataSet>()
    data_sets.add(data_set1)
    data_sets.add(data_set2)

    val datas = BarData(data_sets)
    datas.setValueTextSize(12f)
//    data.setValueTextColor(context.getColorFromRes(R.color.darkgrey))
    datas.setValueTextColor(context.getColorFromRes(R.color.darkgrey))
    datas.setValueTextSize(9f)
    datas.setValueTypeface(ResourcesCompat.getFont(context, R.font.nanum_square_extra_bold))

    datas.setValueFormatter(object :ValueFormatter(){
        override fun getFormattedValue(value: Float): String {
            return if(value>=0)Math.round(value).toString()else ""
        }
    })


    datas.barWidth=0.15f
    this.data=datas
    this.invalidate()
    this.groupBars(-0.5f, 0.5f, 0.1f)




    setAxis(context, this)
    //legend 제거
//    this.legend.isEnabled=false
    this.legendRenderer
    //legend custom
    val legendEntry1 = LegendEntry("첫번째", Legend.LegendForm.LINE, 10f, 2f, null, context.getColorFromRes(R.color.middlegrey))
    val legendEntry2 = LegendEntry("두번째", Legend.LegendForm.LINE, 10f, 2f, null, context.getColorFromRes(R.color.yellow))

    this.legend.setCustom(arrayListOf(legendEntry1, legendEntry2))
    this.legend.isEnabled=true
    this.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
    this.legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
    this.legend.typeface = ResourcesCompat.getFont(context,R.font.nanum_square_extra_bold )
    this.legend.textColor = context.getColorFromRes(R.color.darkgrey)

    //동그란 모
    val renderer=RoundedChartRenderer(this, this.animator, this.viewPortHandler)
    renderer.setmRadius(30f)
    this.renderer = renderer

    this.description.isEnabled=false

//    var max = this.yChartMax
//    drawAxisLine(this, max.toInt())
//    drawAxisLine(this, max.toInt()/2)



}

fun setAxis(context: Context,barchart:BarChart) {
    val x_axis = barchart.xAxis
    val left_axis = barchart.axisLeft
    val right_axis = barchart.axisRight

    val DAYS= arrayListOf<String>("일","월","화","수","목","금","토")

    //x축에 일-월 표시
    x_axis.valueFormatter=object :ValueFormatter(){
        override fun getFormattedValue(value: Float): String {
            return DAYS.get(value.toInt())
        }
    }
    //label은 바닥에 위치하도록
    x_axis.position=XAxis.XAxisPosition.BOTTOM
    x_axis.setDrawGridLines(false)
    x_axis.typeface= ResourcesCompat.getFont(context,R.font.nanum_square_bold )
    x_axis.textSize=11f

    left_axis.setDrawGridLines(false)
    left_axis.setDrawLabels(false)
    left_axis.setDrawAxisLine(false)
    left_axis.axisMinimum=0f
    left_axis.granularity=10f

    right_axis.isEnabled=false



//    left_axis.isEnabled=false
//
//
//    right_axis.setDrawLabels(false)
//    right_axis.setDrawAxisLine(false)
//    right_axis.isEnabled=false)

}

fun getColorFromRes(context: Context, color : Int) :Int{
    return ContextCompat.getColor(context, color)
}
private fun drawAxisLine(barchart: BarChart, num : Int) {
    val line : LimitLine = LimitLine(num.toFloat())
    barchart.axisLeft.addLimitLine(line)
//    line.lineColor= getColorFromRes(R.color.yellow)

}
```

#### drawSingleGraph
```kotlin
// 일요일에서 월요일까지의 데이터를 ArrayList로 전달받아 그래프를 그려주는 함수

fun BarChart.drawSingleGraph(context: Context, datas : ArrayList<Int>, count_noti:Int) {

    this.setTouchEnabled(false)

    var data : BarData = createChartData(context, datas,count_noti)
    configureChartAppearance( this,context)
    prepareChartData(context, this, data)

    //bar 위에 value 위치하도
    this.setDrawValueAboveBar(true)
    //알림 개수 라인 그리기
    if(count_noti > 0){
        drawAxisLine(context, this, count_noti)
    }else{
        this.axisLeft.removeAllLimitLines()
    }
}

// num에 해당하는 value의 수평선 그린
private fun drawAxisLine(context: Context, barchart : BarChart, num : Int) {

    barchart.axisLeft.removeAllLimitLines()
    val line :LimitLine = LimitLine(num.toFloat())
    barchart.axisLeft.addLimitLine(line)
    line.lineColor= context.getColorFromRes(R.color.yellow)
    line.lineWidth=1f
//    barchart.animateX(2000)
//    barchart.animateY(2000)

}

// 데이터 받아서다
private fun prepareChartData(context: Context, barchart : BarChart,data: BarData) {
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

    val set = CustomBarDataSet(values, "SET_LABEL", count_noti)
    set.colors=
            //listOf(ContextCompat.getColor(this,R.color.yellow),ContextCompat.getColor(this, R.color.gray))
        listOf(context.getColorFromRes(R.color.yellow), context.getColorFromRes(R.color.middlegrey))
    val dataSets = ArrayList<IBarDataSet>()
    dataSets.add(set)



    val data:BarData = BarData(dataSets)
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

//chart 가 어떻가 보여질
private fun configureChartAppearance(barchart : BarChart, context: Context) {


    val DAYS = arrayListOf<String>("일","월","화","수","목","금","토")

    barchart.description.isEnabled=false
    barchart.setDrawValueAboveBar(false)

    //legend없애기
    barchart.legend.isEnabled=false

    val renderer=RoundedChartRenderer(barchart, barchart.animator, barchart.viewPortHandler)

    renderer.setmRadius(30f)
    barchart.renderer = renderer



    val x_axis = barchart.xAxis

    //x축 bottom에 위치
    x_axis.position=XAxis.XAxisPosition.BOTTOM
    //x축에 요일 입력
    x_axis.setDrawGridLines(false)
    x_axis.valueFormatter= object : ValueFormatter(){
        override fun getFormattedValue(value:Float): String {
            return DAYS.get(value.toInt())
        }
    }
    x_axis.typeface=ResourcesCompat.getFont(context,R.font.nanum_square_bold )
    x_axis.textSize=11f
//    x_axis.spaceMin = 5f

    //y축의 활성화 없애개
    val axisLeft = barchart.axisLeft
    axisLeft.granularity=10f
    axisLeft.axisMinimum= 0f
    axisLeft.setDrawAxisLine(false)
    axisLeft.setDrawTopYLabelEntry(false)
    axisLeft.setDrawZeroLine(false)
    axisLeft.setDrawLabels(false)
    axisLeft.setDrawGridLines(false)

    val axisRight = barchart.axisRight



    axisRight.isEnabled=false


}

```
ExchangeFoodFragment
```kotlin
fun TextView.categorySetClicked(context: Context){  
  this.background = ContextCompat.getDrawable(context, R.drawable.rec18_yellow)  
  this.setTextColor(context.getColor(R.color.white))  
}  
fun TextView.categorySetUnClicked(context: Context){  
  this.background = null  
 this.setTextColor(context.getColor(R.color.grey))  
}
```


----
<br>

## 💻 Developer

* [김가영](https://github.com/jujube0)
* [전성은](https://github.com/cse0616)
* [정화진](https://github.com/hjh1161514)



