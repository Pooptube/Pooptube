package com.example.pooptube.home

import HomeVideoAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pooptube.R
import java.util.Date
import com.example.pooptube.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeChipAdapter: HomeChipAdapter
    private lateinit var homeVideoAdapter: HomeVideoAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeChipAdapter = HomeChipAdapter()
        homeVideoAdapter = HomeVideoAdapter()

        with(binding) {
            chipRecyclerView.adapter = homeChipAdapter
            chipRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            videoRecyclerView.adapter = homeVideoAdapter
            videoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        // Chip 더미 데이터 생성 및 설정
        val dummyFilterData = List(10) {
            HomeFilterModel(category = "카테고리 $it")
        }
        homeChipAdapter.setItems(dummyFilterData)

        // Video 더미 데이터 생성 및 설정
        val dummyData = List(20) {
            HomeVideoModel(
                imgThumbnail = "", // 여기에 썸네일 URL을 넣을 수 있습니다.
                channelLogoImg = "", // 채널 로고 URL
                title = "동영상 제목 $it",
                author = "채널명",
                count = "100만회",
                dateTime = Date(),
                isFavorite = it % 2 == 0 // 홀수 번째 아이템은 즐겨찾기 상태로 설정
            )
        }
        homeVideoAdapter.setItems(dummyData)
    }
}