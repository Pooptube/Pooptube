package com.example.pooptube.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pooptube.databinding.MyVideosItemBinding
import com.example.pooptube.myvideos.MyVideosAdapter
import com.example.pooptube.myvideos.MyVideosDiffUtil
import com.example.pooptube.myvideos.YoutubeVideoItem
import com.example.pooptube.videodetail.VideoDetailModel

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.VideoHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var listener: OnItemClickListener? = null

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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.VideoHolder {
        val view = MyVideosItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoHolder(view)
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        (holder as VideoHolder).setData(oldItems[position])

        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return oldItems.size
    }

    fun setData(newList: List<YoutubeVideoItem>) {
        val videoDiff = MyVideosDiffUtil(oldItems, newList)
        val diff = DiffUtil.calculateDiff(videoDiff)
        oldItems = newList
        diff.dispatchUpdatesTo(this)
    }
}