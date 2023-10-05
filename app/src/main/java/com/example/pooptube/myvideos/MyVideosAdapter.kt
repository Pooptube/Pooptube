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

    class VideoHolder(itemView: MyVideosItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView

        fun setData(data: VideoDetailModel) {
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
        val videoDetailModel = videoDetailModels[position]
        holder.setData(videoDetailModel)

        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return videoDetailModels.size
    }

    fun setData(newList: List<VideoDetailModel>) {
        videoDetailModels = newList
        notifyDataSetChanged()
    }
}