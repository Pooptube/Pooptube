package com.example.pooptube.videodetail

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.load
import com.example.pooptube.R
import com.example.pooptube.databinding.FragmentVideoDetailBinding
import com.example.pooptube.home.HomeVideoModel
import com.example.pooptube.main.MainActivity
import com.example.pooptube.main.Utils.formatTimeDifference
import com.example.pooptube.main.Utils.formatViewCount
import com.example.pooptube.myvideos.YoutubeVideoItem

class VideoDetailFragment : Fragment() {

    private var _binding: FragmentVideoDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).showToolbar(false)
        (requireActivity() as MainActivity).showTabLayout(false)

        sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        // SharedPreferences 저장 됐는지 확인용
        val likedVideoKeys = sharedPreferences.all.keys.filter { it.startsWith("liked_") }
        for (key in likedVideoKeys) {
            val isLiked = sharedPreferences.getBoolean(key, false)
            Log.d("LikedVideos", "$key: $isLiked")
        }

        val bundle = arguments
        if (bundle != null) {
            val fragmentType = bundle.getInt("fragment", -1)

            when (fragmentType) {
                0 -> {
                    // Home 프래그먼트에서 클릭한 경우
                    val videoData =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            bundle.getParcelable("videoData", HomeVideoModel::class.java)
                        } else {
                            bundle.getParcelable("videoData")
                        }
                    if (videoData != null) {
                        val videoModel = VideoDetailModel(
                            thumbnail = videoData.imgThumbnail,
                            title = videoData.title,
                            channelProfile = videoData.imgThumbnail,
                            channelId = videoData.author,
                            description = videoData.description,
                            dateTime = videoData.dateTime,
                            viewCount = videoData.count,
                            isFavorite = sharedPreferences.getBoolean(
                                "liked_${videoData.title}",
                                false
                            )
                        )
                        bind(videoModel)
                    }
                }

                1 -> {
                    // 다른 프래그먼트에서 클릭한 경우
                    val videoData =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            bundle.getParcelable("videoData", YoutubeVideoItem::class.java)
                        } else {
                            bundle.getParcelable("videoData")
                        }

                    if (videoData != null) {
                        val videoModel = VideoDetailModel(
                            thumbnail = videoData.snippet.thumbnails.medium.url,
                            title = videoData.snippet.title,
                            channelProfile = videoData.snippet.thumbnails.medium.url,
                            channelId = videoData.snippet.channelTitle,
                            description = videoData.snippet.description,
                            dateTime = videoData.snippet.publishedAt,
                            viewCount = videoData.statistics?.viewCount ?: "0",
                            isFavorite = sharedPreferences.getBoolean(
                                "liked_${videoData.snippet.title}",
                                false
                            )
                        )
                        bind(videoModel)
                    }
                }
            }
        }

        binding.videodetailBtnDown.setOnClickListener {
            closeFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as MainActivity).showToolbar(true)
        (requireActivity() as MainActivity).showTabLayout(true)
        _binding = null
    }

    private fun bind(item: VideoDetailModel) = with(binding) {
        videodetailThumbnail.load(item.thumbnail)
        videodetailTitle.text = item.title
        videodetailChannelProfile.load(item.thumbnail)
        videodetailChannelName.text = item.channelId
        videodetailDescription.text = item.description
        videodetailUpdatedDate.text = formatTimeDifference(item.dateTime)
        videodetailViewcount.text = "조회수 ${formatViewCount(item.viewCount.toInt())}"

        favoriteVideo(item)
        shareVideo(item.thumbnail)
    }

    private fun favoriteVideo(viewModel: VideoDetailModel) = with(binding) {
        val btnLike = videodetailLikeContainer
        val likeIcon = videodetailLikeIcon
        var isFavorite = viewModel.isFavorite
        likeIcon.setImageResource(if (isFavorite) R.drawable.ic_like_filled else R.drawable.ic_like_empty)

        btnLike.setOnClickListener {
            isFavorite = !isFavorite
            val newIconResId = if (isFavorite) R.drawable.ic_like_filled else R.drawable.ic_like_empty
            likeIcon.setImageResource(newIconResId)

            val message = if (isFavorite) "좋아요 리스트에 추가되었습니다." else "좋아요 리스트에서 삭제되었습니다."
            showToast(message)

            sharedPreferences.edit().putBoolean("liked_${viewModel.title}", isFavorite).apply()
        }
    }

    private fun closeFragment() {
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun shareVideo(videoUrl: String) {
        val btnShare = binding.videodetailShareContainer
        btnShare.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, videoUrl)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, "공유하기"))
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}