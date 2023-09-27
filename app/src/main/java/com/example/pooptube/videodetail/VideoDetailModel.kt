package com.example.pooptube.videodetail

import java.util.Date

data class VideoDetailModel (
    val thumbnail: String,
    val title: String,
//    val channelProfile: String, // api에 데이터가 안보임...다시 찾아 볼 예정
    val channelId: String,
    val description: String,
    val dateTime: Date,
    val isFavorite: Boolean,
    val viewCount: String
)