package com.example.pooptube.main

import com.google.gson.annotations.SerializedName

data class VideosModel(
    @SerializedName("thumbnails")
    var thumbnail: String,
)