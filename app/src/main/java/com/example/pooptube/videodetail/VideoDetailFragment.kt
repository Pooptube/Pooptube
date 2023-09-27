package com.example.pooptube.videodetail

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pooptube.R
import com.example.pooptube.databinding.FragmentVideoDetailBinding

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

        setupUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupUI() = with(binding){

        videodetailBtnDown.setOnClickListener {
            closeFragment()
        }

        val videoUrl = "임시값"
        videodetailShareContainer.setOnClickListener {
            shareVideo(videoUrl)
        }

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