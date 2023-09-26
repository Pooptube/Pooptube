import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pooptube.databinding.HomeItemVideoBinding
import com.example.pooptube.home.HomeVideoModel

class HomeVideoAdapter : RecyclerView.Adapter<HomeVideoAdapter.VideoViewHolder>() {

    private var items: List<HomeVideoModel> = listOf()

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class VideoViewHolder(private val binding: HomeItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeVideoModel) {
            // 각 요소에 데이터 바인딩
            // Coil 라이브러리를 사용하여 이미지 로딩
            binding.videoThumbnailImageView.load(item.imgThumbnail) {
                crossfade(true)
            }

            binding.titleText.text = item.title
            binding.subTitleText.text = "${item.author} · ${item.count} · ${item.dateTime} · ${if (item.isFavorite) "♡" else ""}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = HomeItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }
    }

    fun setItems(data: List<HomeVideoModel>) {
        this.items = data
        notifyDataSetChanged()
    }
}