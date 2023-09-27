package com.example.pooptube.home

import com.example.pooptube.myvideos.YoutubeVideoItem
import java.util.Date

data class HomeVideoResponse(
    val items: List<YoutubeVideoItem>
)

data class HomeVideoModel(
    val imgThumbnail: String,
    val channelLogoImg: String,
    val title: String,
    val author: String,
    val count: String,
    val dateTime: Date,
)

//data class YoutubeVideoItem(
//    val snippet: Snippet,
//    // Search에서는 Statistics 지원x , video에서는 있
//    val statistics: Statistics?
//)
//
//data class Statistics(
//    val viewCount: String,
//    // 필요하면 다른 통계 정보도 추가
//)
//
//data class Snippet(
//    val title: String,
//    val publishedAt: Date,
//    val description: String,
//    val thumbnails: Thumbnails,
//    val channelTitle: String,
//    val channelId: String
//)
//
//data class Thumbnails(
//    val default: ThumbnailInfo,
//    val medium: ThumbnailInfo,
//    val high: ThumbnailInfo
//)
//
//data class ThumbnailInfo(
//    val url: String,
//    val width: Int,
//    val height: Int
//)

//영상의 제목, 설명, 썸네일, 조회수 등)를 받아올 것인지 지정할 수 있습니다. 이를 지정하는 것이 part 파라미터
//snippet는 영상의 기본 정보(영상 제목, 설명, 썸네일, 채널 정보 등)를 가져오기 위한 파라미터 값
//그런데 만약 영상의 조회수, 좋아요 수, 싫어요 수 등의 통계 정보를 받아오고 싶다면, statistics라는 다른 파라미터 값을 추가로 사용
//영상의 기본 정보와 통계 정보 모두를 요청하려면 part 파라미터에 "snippet,statistics"와 같이 두 값을 모두 지정