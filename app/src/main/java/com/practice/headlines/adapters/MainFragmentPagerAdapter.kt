package com.practice.headlines.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.practice.headlines.R
import com.practice.headlines.fragments.DownloadsFragment
import com.practice.headlines.fragments.NewsFragment
import java.lang.IllegalStateException

class MainFragmentPagerAdapter(lifecycle:Lifecycle,fragmentManager:FragmentManager): FragmentStateAdapter(fragmentManager, lifecycle) {

    companion object {
        private const val NUM_PAGES = 2
    }

    override fun getItemCount(): Int {
       return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->NewsFragment.newInstance()
            1->DownloadsFragment.newInstance()
            else->{
                throw IllegalStateException("There should only be 2 fragments")
            }
        }

    }
}