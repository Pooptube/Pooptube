package com.example.pooptube.myvideos

import com.google.gson.annotations.SerializedName
import java.util.Date


data class VideosModelList(
    @SerializedName("nextPageToken")
    val nextPageToken: String,
    @SerializedName("items")
    val items: List<YoutubeVideoItem>
)

data class YoutubeVideoItem(
    @SerializedName("snippet")
    val snippet: VideoSnippet,
    // Search에서는 Statistics 지원x , video에서는 있
    @SerializedName("statistics")
    val statistics: Statistics?
)

data class Statistics(
    @SerializedName("viewCount")
    val viewCount: String
    // 필요하면 다른 통계 정보도 추가
)

data class VideoSnippet(
    @SerializedName("title")
    val title: String,
    @SerializedName("publishedAt")
    val publishedAt: Date,
    @SerializedName("description")
    val description: String,
    @SerializedName("thumbnails")
    val thumbnails: Thumbnails,
    @SerializedName("channelTitle")
    val channelTitle: String,
    @SerializedName("channelId")
    val channelId: String
)
data class Thumbnails(
    @SerializedName("default")
    val default: ThumbnailInfo,
    @SerializedName("medium")
    val medium: ThumbnailInfo,
    @SerializedName("high")
    val high: ThumbnailInfo
)

data class ThumbnailInfo(
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int
)

