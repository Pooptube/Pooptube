package com.example.pooptube.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pooptube.BuildConfig
import com.example.pooptube.main.ApiConfig
import com.example.pooptube.myvideos.HomeFilterModel
import com.example.pooptube.myvideos.VideosModelList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private val _searchResults = MutableLiveData<VideosModelList>()
    val searchResults: LiveData<VideosModelList> = _searchResults

    val categoryFilterData = listOf(
        HomeFilterModel(category = "음악", categoryId = "10"),
        HomeFilterModel(category = "스포츠", categoryId = "17"),
        HomeFilterModel(category = "게임", categoryId = "20"),
        HomeFilterModel(category = "동물", categoryId = "15"),
        HomeFilterModel(category = "엔터", categoryId = "26"),
        HomeFilterModel(category = "테크", categoryId = "28"),
        HomeFilterModel(category = "뉴스", categoryId = "25")
    )

     fun searchVideoList(query: String, categoryId: String? = null) {
        val client = ApiConfig.getService().searchVideoInfo(BuildConfig.YOUTUBE_API_KEY, "snippet", query, "video", 20, categoryId)
         client.enqueue(object : Callback<VideosModelList> {
            override fun onResponse(call: Call<VideosModelList>, response: Response<VideosModelList>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null && data.items.isNotEmpty()) {
                        _searchResults.postValue(data)
                    }
                }
            }
            override fun onFailure(call: Call<VideosModelList>, t: Throwable) {
                // 실패 시 처리
            }
        })
    }
}