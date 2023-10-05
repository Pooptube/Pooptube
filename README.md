## 💩 프로젝트 명
PoopTube

## 📕 프로젝트 설명
저희조의 시그니쳐인 ‘응가(Poop)’와 미디어를 보며 재밌어서 ‘풉풉’ 하며 웃는 모습의 이중적인 의미를 담은 프로젝트 입니다.
YouTube Data API v3을 이용하여 가장 인기있는 동영상/ 카테고리별 영상 목록을 보여주고, 검색까지 할 수 있는 어플입니다.

## 📅 프로젝트 기간
23.09.25 ~ 23.10.06

## 📱 Application Version
minSdk 26

targetSdk 33

## 💻 기술스택
<img src="https://img.shields.io/badge/Kotlin-7F52FF?style=flat&logo=kotlin&logoColor=white"> <img src="https://img.shields.io/badge/Android-3DDC84?style=flat&logo=android&logoColor=white"> </br>

`JetPack`, `Retrofit`, `ViewModel`, `Coil`, `Coroutine`, `LiveData`, `SharedPreferences`


## 🎬 팀원소개
<table>
  <tbody>
    <tr>
      <td align="center">
        <a href="https://github.com/ouowinnie">
            <img src="https://avatars.githubusercontent.com/u/139089298?v=4" width="100px;"><br /><sub>
            <b>팀장 : 이다을</b><br />
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/ars-yeon">
            <img src="https://avatars.githubusercontent.com/u/68272722?v=4" width="100px;"><br /><sub>
            <b>팀원 : 이소연</b></sub>
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/KyungHwa0">
            <img src="https://avatars.githubusercontent.com/u/124041716?v=4" width="100px;"><br /><sub>
            <b>팀원 : 남경화</b></sub>
        </a>
      </td>        
     <tr/>
  </tbody>
</table>

##
💩 이다을
- SearchFragment
- MyVideoFragment

💩 이소연
- VideoDetailFragment
- ViewPager2, TabLayout

💩 남경화
- HomeFragment

### 기능 설명
#### MainActivity
- ToolBar에 앱 아이콘과 이름 설정
- TabLayout과 ViewPager2를 사용한 프래그먼트 간의 화면 전환
- VideoDetail 열기 위한 함수 설정
  - openVideoDetailFromHome, openVideoDetail, openVideoDetailFromMyVideos

#### HomeFragment
- Most Popular Videos 목록 출력
- Chip을 통해 카테고리별 비디오 조회
  - ViewPager2 와 가로형 RecyclerView로 인해 CustomRecyclerView를 통한 Chip 영역에 ViewPager2 작동x

#### SearchFragment
- Search 엔드 포인트를 활용한 검색 키워드 비디오 목록 출력
- Chip을 통해 검색한 키워드 안에서 카테고리별 비디오 재조회

#### VideoDetailFragment
- 좋아요한 비디오 리스트 가져오기
  - SharedPreferences를 사용하여 저장한 비디오 가져오기

#### MyVideoFragment
- Bundle로부터 데이터 가져오기, 비디오 데이터 바인딩
- 비디오 좋아요 처리
  - 클릭 이벤트 처리로 아이콘의 상태를 변경
  - SharedPreferences를 사용하여 상태 저장
- 공유하기 기능
  - Intent를 사용하여 공유 시트를 열리며 사용자가 다양한 공유 옵션을 선택할 수 있음