package com.example.pooptube.myvideos

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pooptube.databinding.MyVideosItemBinding
import com.example.pooptube.videodetail.VideoDetailModel

class MyVideosAdapter : RecyclerView.Adapter<MyVideosAdapter.VideoHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var listener: OnItemClickListener? = null
    private var videoDetailModels = emptyList<VideoDetailModel>()

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    private var oldItems = emptyList<YoutubeVideoItem>()

    class VideoHolder(itemView: MyVideosItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView

        fun setData(data: YoutubeVideoItem){
            binding.videoTitle.text =  data.snippet.title
            Glide.with(binding.root)
                .load(data.snippet.thumbnails.high.url)
                .into(binding.videoThumbnail)
        }
        fun setSaveData(data: VideoDetailModel) {
            binding.videoTitle.text = data.title
            Glide.with(binding.root)
                .load(data.thumbnail)
                .into(binding.videoThumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVideosAdapter.VideoHolder {
        val view = MyVideosItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoHolder(view)
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        (holder as VideoHolder).setData(oldItems[position])
        holder.setSaveData(videoDetailModels[position])

        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return oldItems.size
    }

    fun setVideoDetailModels(newList: List<VideoDetailModel>) {
        videoDetailModels = newList
        notifyDataSetChanged()
    }

    fun setData(newList: List<YoutubeVideoItem>) {
        val videoDiff = MyVideosDiffUtil(oldItems, newList)
        val diff = DiffUtil.calculateDiff(videoDiff)
        oldItems = newList
        diff.dispatchUpdatesTo(this)
    }
}