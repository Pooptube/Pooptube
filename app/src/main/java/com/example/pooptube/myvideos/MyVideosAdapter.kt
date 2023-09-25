package com.example.pooptube.myvideos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pooptube.databinding.MyVideosItemBinding
import com.example.pooptube.main.VideosModel

class MyVideosAdapter(private val items: List<VideosModel>) : RecyclerView.Adapter<MyVideosAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: MyVideosItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val videoThumbnail = binding.videoThumbnail
        val videoTitle = binding.videoTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVideosAdapter.ViewHolder {
        val binding = MyVideosItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyVideosAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.videoThumbnail.setImageResource(item.videoThumbnail)
        holder.videoTitle.text = item.videoTitle
    }

    override fun getItemCount(): Int {
        return items.size
    }
}