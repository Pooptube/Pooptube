package com.example.pooptube.myvideos

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pooptube.databinding.FragmentMyVideosBinding
import com.example.pooptube.main.MainActivity
import com.example.pooptube.videodetail.VideoDetailModel

class MyVideosFragment : Fragment() {

    private lateinit var binding: FragmentMyVideosBinding
    private var myVideosAdapter = MyVideosAdapter()
    private lateinit var viewModel: MyVideosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyVideosBinding.inflate(layoutInflater)

        binding.vidoesRecyclerView.adapter = myVideosAdapter
        binding.vidoesRecyclerView.layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false)

        viewModel = ViewModelProvider(this).get(MyVideosViewModel::class.java)

        viewModel.likedVideos.observe(viewLifecycleOwner) { updatedlList ->
            myVideosAdapter.setVideoDetailModels(updatedlList)
            Log.d("확인중", "Liked videos updated: ${updatedlList.size} items")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadLikedVideos(requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE))

        myVideosAdapter.setOnItemClickListener(object : MyVideosAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val videoData = viewModel.video.value
                val isPositionInVideoData = position >= 0 && position < videoData?.items?.size!!
                if (videoData != null && isPositionInVideoData) {
                    (requireActivity() as MainActivity).openVideoDetail(videoData, position)
                }
            }
        })
    }
}