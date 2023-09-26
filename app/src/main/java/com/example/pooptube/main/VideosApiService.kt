package com.example.pooptube.main

import com.example.pooptube.myvideos.VideosModelList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface VideosApiService {
    @GET("videos")
    fun getVideoInfo(
        @Query("part") part: String,
        @Query("chart") chart: String,
        @Query("maxResults") maxResults: Int,
        @Query("key") apiKey: String
    ): Call<VideosModelList>
}