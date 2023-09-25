package com.example.pooptube.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.pooptube.R
import com.example.pooptube.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var adapter: MainPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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
}