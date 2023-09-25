package com.example.pooptube.main

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryVideosService {
    @GET("/youtube/v3/videos")
    fun getCategoryVideos(
    @Query("part") part: String,
    @Query("chart") chart: String,
    @Query("maxResults") maxResults: Int,
    @Query("videoCategoryId") categoryId: String,
    @Query("key") apiKey: String
    ): Call<VideosModelList>
}