package com.example.pooptube.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pooptube.home.HomeFragment
import com.example.pooptube.myvideos.MyVideosFragment
import com.example.pooptube.search.SearchFragment

class MainPagerAdapter(activity: MainActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> SearchFragment()
            2 -> MyVideosFragment()
            else -> HomeFragment()
        }
    }
}