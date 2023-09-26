package com.example.pooptube.myvideos

import androidx.recyclerview.widget.DiffUtil

class MyVideosDiffUtil (
    private val oldList: List<YoutubeVideoItem>,
    private val newList: List<YoutubeVideoItem>,

) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldVideo = oldList[oldItemPosition]
        val newVideo = oldList[newItemPosition]
        return oldVideo.snippet.title == newVideo.snippet.title
    }
}