package com.example.pooptube.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.pooptube.myvideos.HomeFilterModel
import androidx.recyclerview.widget.RecyclerView
import com.example.pooptube.databinding.FilterChipBinding

class HomeChipAdapter : RecyclerView.Adapter<HomeChipAdapter.ChipViewHolder>() {

    // 카테고리 목록을 저장하기 위한 변수
    private var items: List<HomeFilterModel> = listOf()
    // 선택된 칩이 없는 상태라서 -1로 설정.
    private var selectedPosition = -1

    interface OnChipClickListener {
        fun onChipClick(position: Int, filterModel: HomeFilterModel?)
    }

    private var listener: OnChipClickListener? = null

    fun setOnChipClickListener(listener: OnChipClickListener) {
        this.listener = listener
    }

    inner class ChipViewHolder(private val binding: FilterChipBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeFilterModel, position: Int) {
            binding.categoryChip.text = item.category

            // 칩 클릭 리스너 설정
            binding.categoryChip.setOnClickListener {
                val previouslySelected = selectedPosition

                // 같은 칩을 다시 선택한 경우
                if (selectedPosition == adapterPosition) {
                    selectedPosition = -1
                    binding.categoryChip.alpha = 0.35f
                    // null을 전달하여 전체 카테고리를 나타냄
                    listener?.onChipClick(position, null)
                } else {
                    selectedPosition = adapterPosition
                    binding.categoryChip.alpha = 1.0f
                    // 칩이 선택되면 리스너 호출
                    listener?.onChipClick(adapterPosition, items[adapterPosition])
                }
                notifyItemChanged(previouslySelected)
                notifyItemChanged(selectedPosition)
            }

            // 선택된 상태에 따른 알파 값(투명도) 조절
            binding.categoryChip.alpha = if (position == selectedPosition) 1.0f else 0.50f

            // 칩 선택 상태 설정
            binding.categoryChip.isChecked = position == selectedPosition
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
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
