package com.example.pooptube.myvideos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pooptube.R
import com.example.pooptube.databinding.FragmentMyVideosBinding
import com.example.pooptube.main.VideosModel

class MyVideosFragment : Fragment() {

    private lateinit var binding: FragmentMyVideosBinding
    private lateinit var adapter: MyVideosAdapter
    private val videosModelList = mutableListOf<VideosModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyVideosBinding.inflate(layoutInflater)

        videosModelList.add(VideosModel(R.drawable.videodetail_sample,"title111"))
        videosModelList.add(VideosModel(R.drawable.videodetail_sample,"title222"))
        videosModelList.add(VideosModel(R.drawable.videodetail_sample,"title333"))
        videosModelList.add(VideosModel(R.drawable.videodetail_sample,"title444"))
        videosModelList.add(VideosModel(R.drawable.videodetail_sample,"title555"))
        videosModelList.add(VideosModel(R.drawable.videodetail_sample,"title666"))

        adapter = MyVideosAdapter(videosModelList)
        binding.vidoesRecyclerView.adapter = adapter
        binding.vidoesRecyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        return binding.root
    }
}