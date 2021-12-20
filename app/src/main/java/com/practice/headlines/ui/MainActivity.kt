package com.practice.headlines.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.practice.headlines.MyViewModelFactory
import com.practice.headlines.Myapplication
import com.practice.headlines.dependencies.CoreComponent
import com.practice.headlines.viewmodel.MainViewModel
import com.practice.headlines.R
import com.practice.headlines.adapters.MainFragmentPagerAdapter
import com.practice.headlines.databinding.ActivityMainBinding
import com.practice.headlines.util.Status
import com.practice.headlines.util.gone
import com.practice.headlines.util.visible


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var coreComponent: CoreComponent
    lateinit var myapplication:Myapplication
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myapplication=this.application as Myapplication
        coreComponent=myapplication.coreComponent
        Log.d("😀",coreComponent.picaaso().toString())
        setupViewPager()
        setupBottomNavigation()
//        val mainViewModel: MainViewModel by viewModels<MainViewModel>{MyViewModelFactory(coreComponent)}
//        mainViewModel.getTopHeadlines().observe(this, Observer{
//            it?.let {resource->
//                when(resource.status){
//                    Status.SUCCESS -> {
//                        binding.progressbar.gone()
//                        Log.d("😀",resource.data.toString())
//                    }
//                    Status.ERROR -> {
//                        resource.message?.let { it1 -> Log.d("😀", it1) }
//                    }
//                    Status.LOADING -> {
//                        Log.d("😀","loading")
//                        binding.progressbar.visible()
//                    }
//                }
//            }
//        })
    }

    fun setupViewPager(){
        binding.viewpager.adapter = MainFragmentPagerAdapter(
            lifecycle = lifecycle,
            fragmentManager = supportFragmentManager
        )
        binding.viewpager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    binding.bottomNavigation.selectedItemId= R.id.news
                }
                else if (position == 1) {
                    binding.bottomNavigation.selectedItemId= R.id.download
                }

                super.onPageSelected(position)
            }
        })
    }


    fun setupBottomNavigation(){
        binding.bottomNavigation.selectedItemId= R.id.news
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.news -> {
                    binding.viewpager.setCurrentItem(0,true)
                    // Respond to navigation item 1 click
                    true
                }
                R.id.download -> {
                    binding.viewpager.setCurrentItem(1,true)
                    // Respond to navigation item 2 click
                    true
                }
                else -> false
            }
        }
    }
}