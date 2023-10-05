package com.example.pooptube.videodetail

import android.os.Parcelable
import com.example.pooptube.myvideos.YoutubeVideoItem
import kotlinx.parcelize.Parcelize
import java.util.Date
@Parcelize
data class VideoDetailModel (
    val thumbnail: String,
    val title: String,
    val channelProfile: String,
    val channelId: String,
    val description: String,
    val viewCount: Int,
//    val videoId: String,
    var isFavorite: Boolean
): Parcelable {
    companion object {
        fun YoutubeVideoItem.toModel() : VideoDetailModel {
            return VideoDetailModel(
                thumbnail = this.snippet.thumbnails.default.url,
                title = this.snippet.title,
                channelProfile = this.snippet.thumbnails.default.url,
                channelId = this.snippet.channelId,
                description = this.snippet.description,
                viewCount = this.statistics?.viewCount?.toInt() ?: 0,
                isFavorite = false
            )
        }
    }
}