package com.example.pooptube.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pooptube.databinding.FragmentHomeBinding
import com.example.pooptube.main.MainActivity
import com.example.pooptube.myvideos.HomeFilterModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeChipAdapter: HomeChipAdapter
    private lateinit var homeVideoAdapter: HomeVideoAdapter
    private val viewModel: HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeChipAdapter = HomeChipAdapter()
        homeVideoAdapter = HomeVideoAdapter()

        homeChipAdapter.setOnChipClickListener(object : HomeChipAdapter.OnChipClickListener {
            override fun onChipClick(position: Int, filterModel: HomeFilterModel?) {
                if (filterModel != null) {
                    // 클릭한 칩에 대한 카테고리만
                    fetchPopularVideos(filterModel.categoryId)
                } else {
                    // 전체 카테고리에 대한 동영상을 불러옴
                    fetchPopularVideos()
                }
            }
        })

        val viewPager2 = (requireActivity() as MainActivity).getViewPager2()

        // ViewPage2 와 가로형 리싸이클러 뷰
        binding.chipRecyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.action) {
                    // 터치를 한 순간 x,y 좌표 저장
                    MotionEvent.ACTION_DOWN -> {
                        startX = e.x
                        startY = e.y
                    }
                    // 터치한 상태에서 움직인 만큼의 x,y 좌표 차이 계산
                    MotionEvent.ACTION_MOVE -> {
                        val diffX = Math.abs(e.x - startX)
                        val diffY = Math.abs(e.y - startY)

                        // 수평으로 움직이는 거리와 수직으로 움직이는 거리를 비교하여
                        // 수평 스크롤인 경우 ViewPager2의 스와이프를 비활성화하고,
                        // 수직 스크롤인 경우 활성화합니다.
                        viewPager2.isUserInputEnabled = diffX <= diffY
                    }
                    // 터치 취소를 감지 하면 ViewPager2의 스와이프를 다시 활성화
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        viewPager2.isUserInputEnabled = true
                    }
                }
                // 리사이클러뷰의 기본 터치 동작을 방해하지 않기 위해 false를 반환
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

            private var startX = 0f
            private var startY = 0f
        })



        homeVideoAdapter.setOnItemClickListener(object : HomeVideoAdapter.OnItemClickListener {
            override fun onItemClick(videoModel: HomeVideoModel) {
                (requireActivity() as MainActivity).openVideoDetailFromHome(videoModel)
            }
        })

        with(binding) {
            chipRecyclerView.itemAnimator = null
            chipRecyclerView.adapter = homeChipAdapter
            chipRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            videoRecyclerView.adapter = homeVideoAdapter
            videoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        // Coroutine을 사용하여 API 호출
        CoroutineScope(Dispatchers.Main).launch {
            fetchPopularVideos()
        }

        // ViewModel에서 categoryFilterData 관리
        homeChipAdapter.setItems(viewModel.categoryFilterData)

        viewModel.videos.observe(viewLifecycleOwner) { videoModels ->
            binding.progressBar.visibility = View.GONE
            homeVideoAdapter.setItems(videoModels)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            binding.progressBar.visibility = View.GONE
            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
        }

        fetchPopularVideos() // 처음에 기본 데이터 로드
    }

    private fun fetchPopularVideos(categoryId: String? = null) {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.fetchPopularVideos(categoryId)
    }
}