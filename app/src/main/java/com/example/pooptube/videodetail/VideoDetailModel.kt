package com.example.pooptube.videodetail

import java.util.Date

data class VideoDetailModel (
    val thumbnail: String,
    val title: String,
//    val channelProfile: String, // api에 데이터가 안보임...다시 찾아야 볼 예정
    val channelId: String,
    val description: String,
    val dateTime: Date,
    val isFavorite: Boolean,
    val likeCount: Int,
    val viewCount: String
)

data class VideoDetailItem(
    val snippet: Snippet,
    val statistics: Statistics?
)

data class Statistics(
    val viewCount: String,
    val likeCount: Int
)

data class Snippet(
    val title: String,
    val publishedAt: Date,
    val description: String,
    val thumbnails: Thumbnails,
    val channelTitle: String,
    val channelId: String
)

data class Thumbnails(
    val default: ThumbnailInfo,
    val medium: ThumbnailInfo,
    val high: ThumbnailInfo
)

data class ThumbnailInfo(
    val url: String,
    val width: Int,
    val height: Int
)