## 아주대학교 2020-1학기 인간과 컴퓨터 상호작용 Project
## CaptionForU

캡션포유를 통해 자막 수요자는 자막게시판에 자신이 원하는 영상의 자막 등록을 요청 할 수 있고, 자막 공급자는 자막 등록을 통해 수익을 얻을 수 있다.
자막 수요자와 공급자는 모두 캡션포유를 사용해서 벌 수있는 포인트와 머니로 여러가지 이득을 볼 수 있다.
또한 언어공부 탭을 통해 자막 스크립트를 다운받아 자신이 원하는 언어를 공부를 할 수 있다.

## Members


아주대학교 소프트웨어학과 201620894 김연교

아주대학교 소프트웨어학과 201720768 김수영

아주대학교 소프트웨어학과 201820776 강희선

## Android Setting

gradle:3.5.2    
compileSdkVersion 29
buildToolsVersion "29.0.2"

## Android External Modules
    implementation 'kr.co.prnd:youtube-player-view:1.3.0' // youtubeplayer API 간소화



    implementation 'com.github.bumptech.glide:glide:4.10.0'

    annotationProcessor 'com.github.bumptech.glide:compiler:4.10.0' // HttpConnection 없이 간단하게 이미지 불러올수있는 glide



    implementation 'com.google.code.gson:gson:2.7'

    implementation 'com.squareup.retrofit2:retrofit:2.1.0'

    implementation 'com.squareup.retrofit2:converter-gson:2.1.0' // Django REST와 간단하게 통신할 수 있는 Retrofit 

## Web Server & DB
Django REST framework & SQLite

