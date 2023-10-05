package com.example.pooptube.main

import com.example.pooptube.myvideos.VideosModelList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface VideosApiService {
    @GET("videos")
    fun getVideoInfo(
        @Query("key") apiKey: String,
        @Query("part") part: String = "snippet, statistics",
        @Query("chart") order: String = "mostPopular",
        @Query("type") type: String = "video",
        @Query("maxResults") maxResults: Int = 20,
        // 카테고리를 필터링하려면 이 값을 설정, 필터링하지 않으려면 null
        @Query("videoCategoryId") categoryId: String? = null,
        // KR만
        @Query("regionCode") regionCode: String = "KR"
    ): Call<VideosModelList>
    @GET("search")
    fun searchVideoInfo(
        @Query("key") apiKey: String,
        @Query("part") part: String = "snippet",
        @Query("q") query: String,
        @Query("type") type: String = "video",
        @Query("maxResults") maxResults: Int = 20,
        @Query("videoCategoryId") categoryId: String? = null
    ): Call<VideosModelList>
}