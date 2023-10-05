package com.example.pooptube.myvideos

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pooptube.BuildConfig
import com.example.pooptube.main.ApiConfig
import com.example.pooptube.videodetail.VideoDetailModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

class MyVideosViewModel : ViewModel() {

    private val _video = MutableLiveData<VideosModelList>()
    val video: LiveData<VideosModelList> = _video

    private val _likedVideos = MutableLiveData<List<VideoDetailModel>>()
    val likedVideos: LiveData<List<VideoDetailModel>> = _likedVideos


    fun getVideoList() {
        val client = ApiConfig.getService().getVideoInfo(BuildConfig.YOUTUBE_API_KEY, "snippet", "mostPopular", "video", 20)
        client.enqueue(object : Callback<VideosModelList>{
            override fun onResponse(call: Call<VideosModelList>, response: Response<VideosModelList>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null && data.items.isNotEmpty()) {
                        _video.postValue(data)
                    }
                }
            }
            override fun onFailure(call: Call<VideosModelList>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    fun loadLikedVideos(sharedPreferences: SharedPreferences) {
        val likedVideoKeys = sharedPreferences.all.keys.filter { it.startsWith("liked_") }
        val likedVideos = mutableListOf<VideoDetailModel>()

        for (key in likedVideoKeys) {
            val isLiked = sharedPreferences.getBoolean(key, false)
            if (isLiked) {
                val title = key.replace("liked_", "")
                val thumbnail = sharedPreferences.getString("thumbnail_$title", "")
                val videoTitle = sharedPreferences.getString("title_$title", "")
                val channelProfile = sharedPreferences.getString("channelProfile_$title", "")
                val channelId = sharedPreferences.getString("channelId_$title", "")
                val description = sharedPreferences.getString("description_$title", "")
                val viewCount = sharedPreferences.getInt("viewCount_$title", 0)
                Log.d("확인중","$thumbnail $videoTitle")
                // VideoDetailModel을 생성하여 likedVideos 리스트에 추가
                val videoDetailModel = VideoDetailModel(
                    thumbnail = thumbnail ?: "",
                    title = videoTitle ?: "",
                    channelProfile = channelProfile ?:"",
                    channelId = channelId ?:"",
                    description = description ?:"",
                    viewCount = viewCount,
                    isFavorite = true
                )
                likedVideos.add(videoDetailModel)
            }
        }
        _likedVideos.value = likedVideos
    }
}