package com.example.pooptube.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pooptube.databinding.FragmentSearchBinding
import com.example.pooptube.main.MainActivity
import com.example.pooptube.home.HomeChipAdapter
import com.example.pooptube.myvideos.HomeFilterModel
import com.example.pooptube.myvideos.MyVideosAdapter
import com.example.pooptube.videodetail.VideoDetailModel.Companion.toModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private var searchAdapter = SearchAdapter()
    private var homeChipAdapter = HomeChipAdapter()
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)

        binding.vidoesRecyclerView.adapter = searchAdapter
        binding.vidoesRecyclerView.layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        viewModel.searchResults.observe(viewLifecycleOwner) {
            if (it != null && it.items.isNotEmpty()) {
                it.items.map { item ->
                    item.toModel()
                }
                searchAdapter.setData(it.items)
            }
        }

        binding.searchBtn.setOnClickListener {
            performSearch()
        }

        binding.searchBar.setOnEditorActionListener{ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                return@setOnEditorActionListener true
            }
            false
        }

        // Coroutine을 사용하여 API 호출
        with(binding) {
            chipRecyclerView.itemAnimator = null
            chipRecyclerView.adapter = homeChipAdapter
            chipRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        homeChipAdapter.setOnChipClickListener(object : HomeChipAdapter.OnChipClickListener {
            override fun onChipClick(position: Int, filterModel: HomeFilterModel?) {
                if (filterModel != null) {
                    // 클릭한 칩에 대한 카테고리만
                    fetchSearchVideos(filterModel.categoryId)
                } else {
                    // 전체 카테고리에 대한 동영상을 불러옴
                    fetchSearchVideos()
                }
            }
        })
        homeChipAdapter.setItems(viewModel.categoryFilterData)

        return binding.root
    }

    private fun fetchSearchVideos(categoryId: String? = null) {
        val query = binding.searchBar.text.toString()
        viewModel.searchVideoList(query, categoryId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchAdapter.setOnItemClickListener(object : SearchAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val videoData = viewModel.searchResults.value
                if (videoData != null && position >= 0 && position < videoData.items.size) {
                    (requireActivity() as MainActivity).openVideoDetail(videoData, position)
                }
            }
        })
    }

    private fun performSearch() {
        val query = binding.searchBar.text.toString()
        if (query.isNotEmpty()) {
            viewModel.searchVideoList(query)
            binding.emptyMsg.visibility = View.GONE
        } else {
            binding.emptyMsg.visibility = View.VISIBLE
            searchAdapter.setData(emptyList())
        }
        hideKeyboardInput(binding.searchBar)
    }

    private fun hideKeyboardInput(view: View) {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}