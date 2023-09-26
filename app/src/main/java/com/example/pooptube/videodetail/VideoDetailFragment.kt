package com.example.pooptube.videodetail

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
}