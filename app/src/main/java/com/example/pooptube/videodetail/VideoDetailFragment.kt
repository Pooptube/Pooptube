package com.example.pooptube.videodetail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import coil.load
import com.example.pooptube.R
import com.example.pooptube.databinding.FragmentVideoDetailBinding
import com.example.pooptube.myvideos.VideosModelList

class VideoDetailFragment : Fragment() {

    private var _binding: FragmentVideoDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
        val bundle = arguments
        if (bundle != null) {
            val videoData =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable("videoData", VideosModelList::class.java)
                } else {
                    bundle.getParcelable("videoData")
                }
            val position = bundle.getInt("position", -1) // 클릭한 위치 가져오기


                if (videoData != null) {
                    val clickedItem = videoData.items[position] // 클릭한 위치의 아이템 데이터 가져오기
                    val thumbnailUrl = clickedItem.snippet.thumbnails.medium.url
                    val title = clickedItem.snippet.title
                    val channelName = clickedItem.snippet.channelTitle
                    val viewCount = clickedItem.statistics?.viewCount ?: "0"
                    val updatedDate = clickedItem.snippet.publishedAt
                    val description = clickedItem.snippet.description

                    videodetailThumbnail.load(thumbnailUrl)
                    videodetailTitle.text = title
                    videodetailChannelProfile.load(thumbnailUrl)
                    videodetailChannelName.text = channelName
                    videodetailViewcount.text = viewCount
                    videodetailUpdatedDate.text = updatedDate.toString()
                    videodetailDescription.text = description

                    videodetailShareContainer.setOnClickListener {
                        shareVideo(thumbnailUrl)  // 비디오 URL로 교체할 예정
                    }
                    favoriteVideo()
                }
            }
            videodetailBtnDown.setOnClickListener {
                closeFragment()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun favoriteVideo() = with(binding){
        var isFavorite = false
        val btnLike = videodetailLikeContainer
        val likeIcon = videodetailLikeIcon
        likeIcon.setImageResource(if (isFavorite) R.drawable.ic_like_filled else R.drawable.ic_like_empty)

        btnLike.setOnClickListener {
            isFavorite = !isFavorite
            val newIconResId = if (isFavorite) {
                showToast("좋아요 리스트에 추가되었습니다")
                R.drawable.ic_like_filled
            } else {
                showToast("좋아요 리스트에서 삭제되었습니다")
                R.drawable.ic_like_empty
            }
            likeIcon.setImageResource(newIconResId)
        }
    }

    private fun closeFragment() {
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun shareVideo(videoUrl: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, videoUrl)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, "공유하기"))
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}