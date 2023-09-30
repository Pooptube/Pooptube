package com.example.pooptube.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pooptube.databinding.FragmentSearchBinding
import com.example.pooptube.main.MainActivity
import com.example.pooptube.home.HomeChipAdapter
import com.example.pooptube.myvideos.HomeFilterModel
import com.example.pooptube.myvideos.MyVideosAdapter

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private var myVideosAdapter = MyVideosAdapter()
    private var homeChipAdapter = HomeChipAdapter()
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)

        binding.vidoesRecyclerView.adapter = myVideosAdapter
        binding.vidoesRecyclerView.layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        viewModel.searchResults.observe(viewLifecycleOwner) {
            if (it != null && it.items.isNotEmpty()) {
                myVideosAdapter.setData(it.items)
            }
        }

        binding.searchBtn.setOnClickListener {
            val query = binding.searchBar.text.toString()
            if (query.isNotEmpty()) {
                viewModel.searchVideoList(query)
                binding.emptyMsg.visibility = View.GONE
            } else {
                binding.emptyMsg.visibility = View.VISIBLE
            }
        }

        // Coroutine을 사용하여 API 호출
        with(binding) {
            chipRecyclerView.itemAnimator = null
            chipRecyclerView.adapter = homeChipAdapter
            chipRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
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
        homeChipAdapter.setItems(categoryFilterData)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myVideosAdapter.setOnItemClickListener(object : MyVideosAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val videoData = viewModel.searchResults.value
                if (videoData != null && position >= 0 && position < videoData.items.size) {
                    (requireActivity() as MainActivity).openVideoDetail(videoData, position)
                    /*val videoId = videoData.items[position].videoId
                    if (videoId != null) {
                        (requireActivity() as MainActivity).openVideoDetailFragment(videoData, videoId, position)
                }*/
                }
            }
        })
    }
}