package com.example.pooptube.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pooptube.main.ApiConfig
import com.example.pooptube.myvideos.VideosModelList
import com.example.pooptube.BuildConfig
import com.example.pooptube.myvideos.HomeFilterModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _videos = MutableLiveData<List<HomeVideoModel>>()
    val videos: LiveData<List<HomeVideoModel>> = _videos

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val apiService = ApiConfig.getService()

    // Chip 카테고리ID 설정
    val categoryFilterData = listOf(
        HomeFilterModel(category = "음악", categoryId = "10"),
        HomeFilterModel(category = "스포츠", categoryId = "17"),
        HomeFilterModel(category = "게임", categoryId = "20"),
        HomeFilterModel(category = "동물", categoryId = "15"),
        HomeFilterModel(category = "엔터", categoryId = "26"),
        HomeFilterModel(category = "테크", categoryId = "28"),
        HomeFilterModel(category = "뉴스", categoryId = "25")
    )

    fun fetchPopularVideos(categoryId: String? = null) {
        val call = apiService.getVideoInfo(apiKey = BuildConfig.YOUTUBE_API_KEY, categoryId = categoryId, regionCode = "KR")

        call.enqueue(object : Callback<VideosModelList> {
            override fun onResponse(call: Call<VideosModelList>, response: Response<VideosModelList>) {
                if (response.isSuccessful) {
                    val videoItems = response.body()?.items ?: emptyList()
                    val videoModels = videoItems.map { videoItem ->
                        HomeVideoModel(
                            imgThumbnail = videoItem.snippet.thumbnails.medium.url,
                            channelLogoImg = "...", // 쓸 수 있어서 남겨둠
                            title = videoItem.snippet.title,
                            description = videoItem.snippet.description,
                            author = videoItem.snippet.channelTitle,
                            count = videoItem.statistics?.viewCount ?: "0",
                            dateTime = videoItem.snippet.publishedAt
                        )
                    }
                    _videos.postValue(videoModels)
                } else {
                    _error.postValue("Error fetching videos")
                }
            }

            override fun onFailure(call: Call<VideosModelList>, t: Throwable) {
                _error.postValue("Error: ${t.message}")
            }
        })
    }
}