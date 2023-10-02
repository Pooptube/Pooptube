package com.example.pooptube.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pooptube.BuildConfig
import com.example.pooptube.main.ApiConfig
import com.example.pooptube.myvideos.VideosModelList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private val _searchResults = MutableLiveData<VideosModelList>()
    val searchResults: LiveData<VideosModelList> = _searchResults

    fun searchVideoList(query: String) {
        val client = ApiConfig.getService().searchVideoInfo(BuildConfig.YOUTUBE_API_KEY, "snippet", query, 20)
        client.enqueue(object : Callback<VideosModelList> {
            override fun onResponse(
                call: Call<VideosModelList>,
                response: Response<VideosModelList>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null && data.items.isNotEmpty()) {
                        _searchResults.value = data
                    }
                }
            }

            override fun onFailure(call: Call<VideosModelList>, t: Throwable) {
                // 실패 시 처리
                Log.d("searchview", "$t")
            }
        })
    }
}