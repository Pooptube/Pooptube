package com.example.pooptube.main

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

        val customToolbar = layoutInflater.inflate(R.layout.custom_toolbar, null)
        val logoImage = customToolbar.findViewById<ImageView>(R.id.toolbar_logo)
        val title = customToolbar.findViewById<TextView>(R.id.toolbar_title)

        logoImage.setImageResource(R.drawable.ic_poop)
        title.text = "PoopTube"

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
}