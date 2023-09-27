package com.example.pooptube.home

import HomeVideoAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pooptube.BuildConfig
import com.example.pooptube.databinding.FragmentHomeBinding
import com.example.pooptube.main.ApiConfig
import com.example.pooptube.main.MainActivity
import com.example.pooptube.myvideos.HomeFilterModel
import com.example.pooptube.myvideos.VideosModelList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeChipAdapter: HomeChipAdapter
    private lateinit var homeVideoAdapter: HomeVideoAdapter
    private val apiService = ApiConfig.getService()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeChipAdapter = HomeChipAdapter()
        homeVideoAdapter = HomeVideoAdapter()

        homeChipAdapter.setOnChipClickListener(object : HomeChipAdapter.OnChipClickListener {
            override fun onChipClick(position: Int, filterModel: HomeFilterModel?) {
                if (filterModel != null) {
                    // 클릭한 칩에 대한 카테고리만
                    fetchPopularVideos(filterModel.categoryId)
                } else {
                    // 전체 카테고리에 대한 동영상을 불러옴
                    fetchPopularVideos()
                }
            }
        })

        homeVideoAdapter.setOnItemClickListener(object : HomeVideoAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                (requireActivity() as MainActivity).openVideoDetailFragment()
            }
        })

        with(binding) {
            chipRecyclerView.itemAnimator = null
            chipRecyclerView.adapter = homeChipAdapter
            chipRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            videoRecyclerView.adapter = homeVideoAdapter
            videoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        // Coroutine을 사용하여 API 호출
        CoroutineScope(Dispatchers.Main).launch {
            fetchPopularVideos()
        }

        // Chip 카테고리ID 설정
        val categoryFilterData = listOf(
            HomeFilterModel(category = "음악", categoryId = "10"),
            HomeFilterModel(category = "스포츠", categoryId = "17"),
            HomeFilterModel(category = "게임", categoryId = "20"),
            HomeFilterModel(category = "동물", categoryId = "15"),
            HomeFilterModel(category = "여행", categoryId = "19"),
            HomeFilterModel(category = "VLOG", categoryId = "21"),
            HomeFilterModel(category = "영화", categoryId = "30"),
            HomeFilterModel(category = "뉴스", categoryId = "25")
        )
        homeChipAdapter.setItems(categoryFilterData)
    }

    private fun fetchPopularVideos(categoryId: String? = null) {
        val call = apiService.getVideoInfo(apiKey = BuildConfig.YOUTUBE_API_KEY, categoryId = categoryId, regionCode = "KR")

        call.enqueue(object : Callback<VideosModelList> {
            override fun onResponse(call: Call<VideosModelList>, response: Response<VideosModelList>) {
                if (response.isSuccessful) {
                    val videoItems = response.body()?.items ?: emptyList()

                    // YoutubeVideoItem 리스트를 HomeVideoModel 리스트로 변환
                    val videoModels = videoItems.map { videoItem ->
                        HomeVideoModel(
                            imgThumbnail = videoItem.snippet.thumbnails.medium.url,
                            channelLogoImg = "...", // 이부분은 추후에 적절한 값을 넣어야 합니다.
                            title = videoItem.snippet.title,
                            author = videoItem.snippet.channelTitle,
                            count = videoItem.statistics?.viewCount ?: "0",
                            dateTime = videoItem.snippet.publishedAt, // API의 응답을 바탕으로 수정했습니다.
                            isFavorite = false
                        )
                    }

                    // 변환된 HomeVideoModel 리스트를 어댑터에 적용
                    homeVideoAdapter.setItems(videoModels)
                } else {
                    // 오류 처리
                    Toast.makeText(requireContext(), "Error fetching videos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<VideosModelList>, t: Throwable) {
                // 네트워크 오류나 데이터 처리 오류 등의 실패 사유를 처리합니다.
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

