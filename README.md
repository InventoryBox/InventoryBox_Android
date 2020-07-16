
# InventoryBox_Android
🦖android🦖
<br>


# 📦 재고창고
<img style="border: 1px solid black !important; border-radius:20px; " src="https://user-images.githubusercontent.com/63707317/86824314-f1be6380-c0c8-11ea-8893-e5856316f338.png" width="200px" />

###  개인 외식 사업자를 위한 재고관리 Application 📦
<b>'나만의 다이어리를 관리하듯 매일매일 쉽게 기록하고 성장할 수 있는 재고관리 플랫폼', 재고창고입니다. </b><br/>
발주시점을 놓치지 않게 도와주는 발주 알림 기능, 데이터를 쉽게 축적할 수 있는 재고 기록 기능이 있습니다. 
또한，재고교환 기능을 통해 재고가 떨어지는 갑작스러운 상황에도 개인 사업자들간 네트워크 형성을 통해 재고를 보충할 수 있습니다.
 <br>

## 🔍 Project
* SOPT 26th APPJAM 
* 프로젝트 기간: 2020.06.13 ~ 2020.07.18

<img style="border: 1px solid black !important; border-radius:20px; " src="https://user-images.githubusercontent.com/63707317/86822421-92f7ea80-c0c6-11ea-965f-0d14951ce44e.png" width="600px" />
<br>

## 📝 개발 환경
##### * Android Studio, kotlin
<br>

## 📝 사용 라이브러리  
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
    
## 📝 기능 소개
| 기능 | 상세 기능 | 담당 |
 |:--------|:--------|:--------:| 
 | 스플레시 | 스플레시 화면 | 전성은 | 
 | 로그인 | 로그인 | 김가영 | 
 | 회원가입 | 이메일 인증 | 김가영 |
 | 홈 | 재고 목록 | 전성은 | 
 |  | 발주 확인 | 전성은 |
 |  | 체크박스 | 전성은 |
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
 <br>

## 📝 프로젝트 구조
#### package 분류
activity, fragment, adapter, viewholder, data, etc 등으로 분류
<div>
<img src="https://user-images.githubusercontent.com/51014789/86890258-ff5f0200-c137-11ea-9c0d-9a2b7186c357.PNG" width="30%">
<img src="https://user-images.githubusercontent.com/51014789/86890495-5b298b00-c138-11ea-9524-b49bc3198f9e.PNG" width="30%">
</div>
 
<br>

## 📝 핵심 기능 구현 방법 및 구현 화면
#### <회원가입 및 로그인>




#### <홈>

recyclerview, viewholder와 HomeOrderData를 사용하여 발주 확인 목록을 오늘 발주할 재고 확인 메모에 표시
<div>
<img src="https://user-images.githubusercontent.com/51014789/86896139-82845600-c140-11ea-856b-2d6f2bb3d5aa.PNG" width="23%">
<img src="https://user-images.githubusercontent.com/51014789/86896147-86b07380-c140-11ea-8d2a-4bd247ec5d36.PNG" width="23%">
<img src="https://user-images.githubusercontent.com/51014789/86896766-70ef7e00-c141-11ea-96e3-8e67acb837ca.PNG" width="23%">
</div>
<br>  
  
#### <재고 기록>

recyclerview, viewholder와 RecordCompletedData를 사용하여 재고를 기록하고 수정
<div>
<img src="https://user-images.githubusercontent.com/61824695/86924051-e53e1780-c169-11ea-9de8-82bd2250c690.png" width="23%">
</div>
<br>

#### <재고량 추이>
graph - MPAndriodChart 라이브러리 이용, BarChart 확장함수 만들어 적용
<div>
<img src="https://user-images.githubusercontent.com/51014789/86896184-962fbc80-c140-11ea-9e49-081a7265bc3c.PNG" width="23%">
</div>
<br>

#### <재고 교환>
<div>
<img src="https://user-images.githubusercontent.com/51014789/86933275-56cf9300-c175-11ea-85b6-52a05e13944c.PNG" width="23%">
</div>
<br>
 
## A-1. ConstraintLayout을 사용한 화면 개발
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
각 항목들을 프로필 constraintlayout과 chain으로 연결하고 Vertical chainStyle을 packed로 지정하여 붙임

* activity_drawer.xml에서 match_constraint 속성 활용
레이아웃에 각 메뉴들을 꽉 차게 맞추기 위해 모든 메뉴들의 layut_width에 0dp로 match_constraint 속성을 적용함

* activity_add.xml에서 guidline 속성 활용

* fragment_graph_detail.xml 에서 guideline 속성, match_constraint 사용

<br>

* fragment_graph_detail.xml 에서 guideline 속성 활용
왼쪽에 같은 margin 값을 주기 위해 guideline을 만든 후 constraint 적용
<br>

### 2. 제약조건의 연관성
뷰를 부모와 연관지어 여백을 적용하는 방식이 아니라 가까운 뷰에게 제약조건을 걸어 여백을 통해 위치를 지정함

<br>

### 3. width, height 속성에 match_parent, wrap_content, match_constraint 위주로 사용

* textView의 text 내용에 따라 크기가 달라져야 하는 경우가 많기 때문에 width 속성에 wrap_content 속성 위주로 사용
ex) 사용자 이름, 주소, 날짜, 발주 확인 목록 등
* activity_drawer.xml에 match_constraint를 활용하여 레이아웃에 각 메뉴들을 꽉 차게 지정
* 회원가입, 로그인 뷰에서 match_constraint를 활용하여 guideline에 각 editText 뷰들을 꽉 차게 지정

<br>

## A-2. kotlin collection의 확장함수 사용 / custom 확장 함수 사용

### custon 확장 함수 사용
##### customEnqueue

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
<br>

----
<br>

## 💻 Developer

* [김가영](https://github.com/jujube0)
* [전성은](https://github.com/cse0616)
* [정화진](https://github.com/hjh1161514)



