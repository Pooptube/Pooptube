package com.example.pooptube.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pooptube.databinding.FilterChipBinding
import com.google.android.material.chip.Chip

class HomeChipAdapter : RecyclerView.Adapter<HomeChipAdapter.ChipViewHolder>() {

    // 카테고리 목록을 저장하기 위한 변수
    private var items: List<HomeFilterModel> = listOf()

    inner class ChipViewHolder(private val binding: FilterChipBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeFilterModel) {
            binding.categoryChip.text = item.category
        }
    }

    // 리스트 데이터 설정
    fun setItems(items: List<HomeFilterModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipViewHolder {
        val binding = FilterChipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChipViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChipViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
