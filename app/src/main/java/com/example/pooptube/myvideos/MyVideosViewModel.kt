package com.example.pooptube.myvideos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pooptube.BuildConfig
import com.example.pooptube.main.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyVideosViewModel : ViewModel() {

    private val _video = MutableLiveData<VideosModelList>()
    val video: LiveData<VideosModelList> = _video

    init {
        getVideoList()
    }

    private fun  getVideoList() {
        val client = ApiConfig.getService().getVideoInfo(BuildConfig.YOUTUBE_API_KEY, "snippet", "mostPopular", "video", 10)
        client.enqueue(object : Callback<VideosModelList>{
            override fun onResponse(
                call: Call<VideosModelList>,
                response: Response<VideosModelList>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null && data.items.isNotEmpty()) {
                        _video.value = data
                    }
                }
            }

            override fun onFailure(call: Call<VideosModelList>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}