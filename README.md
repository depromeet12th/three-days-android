<div align="center">

## 🙋Android Developers

|                           Android                            |                           Android                            |                           Android                            |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
|  <img width=300 src="https://avatars.githubusercontent.com/u/76620764?v=4"> | <img width=300 src="https://avatars.githubusercontent.com/u/68214704?v=4">| <img width=300 src="https://avatars.githubusercontent.com/u/4813025?v=4"> |
|             [김주환](https://github.com/juhwankim-dev)             |             [김혜인](https://github.com/kimhyeing)              |            [전해성](https://github.com/junhaesung)            |



## 🛠️ Tech Stack

**Communication**

<img src="https://img.shields.io/badge/Android Studio-6DB33F?style=flat-square&logo=android&logoColor=white"/>
<img src="https://img.shields.io/badge/Kotlin-CC0200?style=flat-square&logo=Kotlin&logoColor=white"/>
<img src="https://img.shields.io/badge/Firebase-FF9E0F?style=flat-square&logo=Firebase&logoColor=white"/>
<img src="https://img.shields.io/badge/Jetpack-6DB33F?style=flat-square&logo=jetpack&logoColor=white"/>
<img src="https://img.shields.io/badge/Hilt-2496ED?style=flat-square&logo=logoColor=white"/>
<img src="https://img.shields.io/badge/Coroutine-2496ED?style=flat-square&logo=logoColor=white"/>
<img src="https://img.shields.io/badge/Retrofit2-2496ED?style=flat-square&logo=logoColor=white"/>
<img src="https://img.shields.io/badge/Flow-2496ED?style=flat-square&logo=logoColor=white"/>

## 🏢Clean Architecture
<img alt="image" src="https://user-images.githubusercontent.com/76620764/212266791-2a36e29f-cb6d-4164-8c2b-f4c5fd291ed5.png">
짝심삼일 앱의 아키텍처입니다.
크게 안드로이드 공식문서에서 권장하는 data, domain, presentation 모듈로 나누었습니다. <br>
또한 공통 로직과 디자인을 포함하는 Core 모듈을 만들고 <br>
presentation 레이어를 기능별로 세분화하여 유지 보수성을 높였습니다.

</div>

## 🗺️Information Architecture(IA)

![I A](https://user-images.githubusercontent.com/78407939/209675815-7e1e6669-16b2-4944-aaae-baaf5cf8f485.png)

## 📱App ScreenShot

<img width="1359" alt="스크린샷 2023-01-13 오후 5 16 40" src="https://user-images.githubusercontent.com/76620764/212271559-e125bb27-6c81-414d-90ac-5dde56f84a24.png">
<img width="1155" alt="스크린샷 2023-01-13 오후 5 16 54" src="https://user-images.githubusercontent.com/76620764/212271781-45a863e1-ecf9-4326-a56e-55df6e067261.png">
<img width="945" alt="스크린샷 2023-01-13 오후 5 18 11" src="https://user-images.githubusercontent.com/76620764/212271800-5cbaab3d-9be4-44a1-8d83-8d9a1d596cde.png">
<img width="1081" alt="스크린샷 2023-01-13 오후 5 18 20" src="https://user-images.githubusercontent.com/76620764/212271827-a6a71a21-e574-43f1-a57d-057825c5ec01.png">


### 카카오 SDK 사용을 위한 해시키 등록 방법
#### Key Hash 조회
```shell
# keystore 파일 위치에 맞게 지정
$ KEYSTORE_PATH=./debug.keystore 
$ keytool -exportcert -alias androiddebugkey -keystore $KEYSTORE_PATH | openssl sha1 -binary | openssl base64
```

#### debug.keystore 새로 만들기
```bash
$ keytool -genkey -v -keystore debug.keystore -storepass android -alias androiddebugkey -keypass android -keyalg RSA -keysize 2048 -validity 10000
```
