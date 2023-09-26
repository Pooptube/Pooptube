package com.example.pooptube.myvideos

import com.google.gson.annotations.SerializedName

data class ThumbnailsYt(
    @SerializedName("high")
    val high: High
) {
    data class High(
        @SerializedName("url")
        val url: String
    )
}