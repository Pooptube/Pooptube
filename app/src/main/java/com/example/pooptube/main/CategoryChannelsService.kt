package com.example.pooptube.main

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryChannelsService {
    @GET("/youtube/v3/videos")
    fun getCategoryChannels(
    @Query("part") part: String,
    @Query("categoryId") categoryId: String,
    @Query("maxResults") maxResults: Int,
    @Query("key") apiKey: String
    ): Call<VideosModelList>
}