package com.example.pooptube.myvideos

import com.google.gson.annotations.SerializedName

data class SnippetYt(
    @SerializedName("title")
    val title: String,
    @SerializedName("thumbnails")
    val thumbnails: ThumbnailsYt
)
