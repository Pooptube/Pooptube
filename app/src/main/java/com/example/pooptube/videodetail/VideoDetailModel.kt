package com.example.pooptube.videodetail

import java.util.Date

data class VideoDetailModel (
    val thumbnail: String,
    val title: String,
    val channelProfile: String,
    val channelId: String,
    val description: String,
    val dateTime: Date,
    val viewCount: String,
//    val videoId: String,
    var isFavorite: Boolean
)