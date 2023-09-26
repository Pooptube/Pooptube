package com.example.pooptube.myvideos

import com.google.gson.annotations.SerializedName


data class VideosModelList(
    @SerializedName("nextPageToken")
    val nextPageToken: String,
    @SerializedName("items")
    val items: List<VideosModel>
) {
    data class VideosModel(
        @SerializedName("snippet")
        val snippet: SnippetYt,
    )
}


