package com.example.pooptube.home

import java.util.Date

data class HomeVideoModel(
    val imgThumbnail: String,
    val channelLogoImg: String,
    val title: String,
    val author: String,
    val count: String,
    val dateTime: Date,
    val isFavorite: Boolean
)
