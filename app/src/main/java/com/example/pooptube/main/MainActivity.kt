package com.example.pooptube.main

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.commit
import androidx.viewpager2.widget.ViewPager2
import com.example.pooptube.R
import com.example.pooptube.databinding.ActivityMainBinding
import com.example.pooptube.home.HomeVideoModel
import com.example.pooptube.myvideos.VideosModelList
import com.example.pooptube.videodetail.VideoDetailFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var toolbar: Toolbar
    private lateinit var adapter: MainPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        toolbar = binding.mainToolbar
        setSupportActionBar(toolbar)

        val customToolbar: View? = layoutInflater.inflate(R.layout.custom_toolbar, toolbar, false)
        val logoImage = customToolbar?.findViewById<ImageView>(R.id.toolbar_logo)
        val title = customToolbar?.findViewById<TextView>(R.id.toolbar_title)

        logoImage?.setImageResource(R.drawable.ic_poop)
        title?.text = "PoopTube"

        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.customView = customToolbar

        viewPager = binding.mainViewpager
        adapter = MainPagerAdapter(this)
        viewPager.adapter = adapter

        tabLayout = binding.mainTablayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Home"
                    tab.setIcon(R.drawable.ic_home)
                }

                1 -> {
                    tab.text = "Search"
                    tab.setIcon(R.drawable.ic_search)

                }

                2 -> {
                    tab.text = "My Videos"
                    tab.setIcon(R.drawable.ic_dashboard)
                }
            }
        }.attach()

        fun TabLayout.Tab.getCustomTextView(): TextView? {
            val customView = this.customView ?: return null
            return customView.findViewById(R.id.main_tablayout) as? TextView
        }

        // 선택된 탭과 선택되지 않은 탭의 색상
        val selectedColor = Color.WHITE
        val unselectedColor = Color.parseColor("#C47474")

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    it.icon?.setTint(selectedColor)
                    it.getCustomTextView()?.setTextColor(selectedColor)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.let {
                    it.icon?.setTint(unselectedColor)
                    it.getCustomTextView()?.setTextColor(unselectedColor)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // 재선택시 동작
            }
        })

        // 초기 상태 설정
        for (i in 0 until tabLayout.tabCount) {
            tabLayout.getTabAt(i)?.icon?.setTint(unselectedColor)
            tabLayout.getTabAt(i)?.view?.findViewById<TextView>(android.R.id.text1)?.setTextColor(unselectedColor)
        }

        // 현재 선택된 탭의 색상 설정
        tabLayout.getTabAt(tabLayout.selectedTabPosition)?.icon?.setTint(selectedColor)
        tabLayout.getTabAt(tabLayout.selectedTabPosition)?.view?.findViewById<TextView>(android.R.id.text1)?.setTextColor(selectedColor)
    }



    fun openVideoDetailFromHome(videoModel: HomeVideoModel) {
        val fragment = VideoDetailFragment()
        val bundle = Bundle()

        bundle.putInt("fragment", 0)
        bundle.putParcelable("videoData", videoModel)
        fragment.arguments = bundle

        supportFragmentManager.commit {
            replace(R.id.video_detail_container, fragment)
            addToBackStack(null)
        }
    }

    fun openVideoDetail(videoData: VideosModelList, position: Int) {
        val fragment = VideoDetailFragment()
        val bundle = Bundle()

        bundle.putInt("fragment", 1)
        bundle.putParcelable("videoData", videoData)
//        bundle.putString("videoId", videoId)
        bundle.putInt("position", position)
        fragment.arguments = bundle

        supportFragmentManager.commit {
            replace(R.id.video_detail_container, fragment)
            addToBackStack(null)
        }
    }

    fun showToolbar(show: Boolean) {
        if (show) toolbar.visibility = View.VISIBLE
        else toolbar.visibility = View.GONE
    }

    fun showTabLayout(show: Boolean) {
        if (show) tabLayout.visibility = View.VISIBLE
        else tabLayout.visibility = View.GONE
    }
    
    fun getViewPager2(): ViewPager2 {
        return viewPager
    }
}