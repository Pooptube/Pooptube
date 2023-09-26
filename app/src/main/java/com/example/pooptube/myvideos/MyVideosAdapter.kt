package com.example.pooptube.myvideos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pooptube.databinding.MyVideosItemBinding

class MyVideosAdapter : RecyclerView.Adapter<MyVideosAdapter.VideoHolder>() {

    private var oldItems = emptyList<VideosModelList.VideosModel>()
    class VideoHolder(itemView: MyVideosItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView

        fun setData(data: VideosModelList.VideosModel){
            binding.videoTitle.text =  data.snippet.title
            Glide.with(binding.root)
                .load(data.snippet.thumbnails.high.url)
                .into(binding.videoThumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVideosAdapter.VideoHolder {
        val view = MyVideosItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoHolder(view)
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        (holder as VideoHolder).setData(oldItems[position])
    }

    override fun getItemCount(): Int {
        return oldItems.size
    }

    fun setData(newList: List<VideosModelList.VideosModel>) {
        val videoDiff = MyVideosDiffUtil(oldItems, newList)
        val diff = DiffUtil.calculateDiff(videoDiff)
        oldItems = newList
        diff.dispatchUpdatesTo(this)
    }
}