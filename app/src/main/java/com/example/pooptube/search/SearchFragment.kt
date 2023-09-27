package com.example.pooptube.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pooptube.databinding.FragmentSearchBinding
import com.example.pooptube.myvideos.ItemSpacingDecoration
import com.example.pooptube.myvideos.MyVideosAdapter

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private var adapter = MyVideosAdapter()
    private lateinit var viewModel: SearchViewModel
    private val itemSpacingDecoration = ItemSpacingDecoration(10)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)

        binding.vidoesRecyclerView.adapter = adapter
        binding.vidoesRecyclerView.layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        viewModel.searchResults.observe(viewLifecycleOwner) {
            if (it != null && it.items.isNotEmpty()) {
                adapter.setData(it.items)
                if (binding.vidoesRecyclerView.itemDecorationCount == 0) {
                    binding.vidoesRecyclerView.addItemDecoration(itemSpacingDecoration)
                }
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
        return binding.root
    }
}