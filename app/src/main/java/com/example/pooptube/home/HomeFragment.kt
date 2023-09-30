package com.example.pooptube.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pooptube.databinding.FragmentHomeBinding
import com.example.pooptube.main.MainActivity
import com.example.pooptube.myvideos.HomeFilterModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeChipAdapter: HomeChipAdapter
    private lateinit var homeVideoAdapter: HomeVideoAdapter
    private val viewModel: HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            override fun onItemClick(videoModel: HomeVideoModel) {
                (requireActivity() as MainActivity).openVideoDetailFromHome(videoModel)
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

        // ViewModel에서 categoryFilterData 관리
        homeChipAdapter.setItems(viewModel.categoryFilterData)

        viewModel.videos.observe(viewLifecycleOwner) { videoModels ->
            binding.progressBar.visibility = View.GONE
            homeVideoAdapter.setItems(videoModels)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            binding.progressBar.visibility = View.GONE
            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
        }

        fetchPopularVideos() // 처음에 기본 데이터 로드
    }

    private fun fetchPopularVideos(categoryId: String? = null) {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.fetchPopularVideos(categoryId)
    }
}