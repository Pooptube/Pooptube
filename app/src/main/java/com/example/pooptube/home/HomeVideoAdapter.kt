package com.example.pooptube.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pooptube.databinding.HomeItemVideoBinding
import java.util.Date

class HomeVideoAdapter : RecyclerView.Adapter<HomeVideoAdapter.VideoViewHolder>() {

    private var items: List<HomeVideoModel> = listOf()

    interface OnItemClickListener {
        fun onItemClick(videoModel: HomeVideoModel)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class VideoViewHolder(private val binding: HomeItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeVideoModel) {

            // 각 요소에 데이터 바인딩
            // Coil 라이브러리를 사용하여 이미지 로딩
            binding.videoThumbnailImageView.load(item.imgThumbnail) {
                crossfade(true)
            }

            binding.titleText.text = item.title

            val currentTime = Date()

            // 현재 시간과 게시된 시간 사이의 차이를 계산합니다.
            val timeDifferenceMillis = currentTime.time - item.dateTime.time
            val timeDifferenceSeconds = timeDifferenceMillis / 1000
            val timeDifferenceMinutes = timeDifferenceSeconds / 60
            val timeDifferenceHours = timeDifferenceMinutes / 60
            val timeDifferenceDays = timeDifferenceHours / 24
            val timeDifferenceMonths = timeDifferenceDays / 30 // 한 달을 30일로 가정
            val timeDifferenceYears = timeDifferenceDays / 365 // 1년을 365일로 가정

            // "몇 일 전", "몇 시간 전", "몇 분 전", "몇 년 전", "몇 개월 전" 등으로 표시할 문자열을 생성합니다.
            val formattedDateTime = when {
                timeDifferenceYears >= 1 -> "${(timeDifferenceYears + 0.5).toInt()}년 전"
                timeDifferenceMonths >= 1 -> "${(timeDifferenceMonths + 0.5).toInt()}개월 전"
                timeDifferenceDays >= 1 -> "${(timeDifferenceDays + 0.5).toInt()}일 전"
                timeDifferenceHours >= 1 -> "${(timeDifferenceHours + 0.5).toInt()}시간 전"
                timeDifferenceMinutes >= 1 -> "${(timeDifferenceMinutes + 0.5).toInt()}분 전"
                else -> "방금 전"
            }
            // 결과를 텍스트뷰에 설정합니다.
            binding.subTitleText.text = "${item.author} · 조회수 ${item.count} · $formattedDateTime"

            itemView.setOnClickListener {
                listener?.onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding =
            HomeItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            listener?.onItemClick(items[position])
        }
    }

    fun setItems(data: List<HomeVideoModel>) {
        this.items = data
        notifyDataSetChanged()
    }
}
