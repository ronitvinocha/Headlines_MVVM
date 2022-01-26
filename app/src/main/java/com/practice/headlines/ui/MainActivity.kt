package com.practice.headlines.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.practice.headlines.MyViewModelFactory
import com.practice.headlines.Myapplication
import com.practice.headlines.dependencies.CoreComponent
import com.practice.headlines.viewmodel.MainViewModel
import com.practice.headlines.R
import com.practice.headlines.adapters.MainFragmentPagerAdapter
import com.practice.headlines.databinding.ActivityMainBinding
import com.practice.headlines.model.Articles
import com.practice.headlines.persistance.Article
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
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController
        val bottomNav = binding.bottomNavigation
        val optionsdownload = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .setPopUpTo(navController.graph.startDestination, false)
            .build()
        val optionsnews = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.slide_in_left)
            .setExitAnim(R.anim.slide_out_right)
            .setPopEnterAnim(R.anim.slide_in_right)
            .setPopExitAnim(R.anim.slide_out_left)
            .build()
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.news -> {
                    navController.navigate(R.id.news, null, optionsnews)
                }
                R.id.download -> {
                    navController.navigate(R.id.download, null, optionsdownload)
                }
            }
            true
        }
        bottomNav.setOnItemReselectedListener { item ->
            return@setOnItemReselectedListener
        }
//        setupViewPager()
//        setupBottomNavigation()
        val mainViewModel: MainViewModel by viewModels{MyViewModelFactory(coreComponent)}
        mainViewModel.articlesFromDB.observe(this, Observer{
            it?.let {resource->
                when(resource.status){
                    Status.SUCCESS -> {
                        val badge = binding.bottomNavigation.getOrCreateBadge(R.id.download)
                        badge.isVisible = true
                        val articles:List<Articles> = resource.data as List<Articles>
                        Log.d("🤡",articles.size.toString())
                        badge.number = articles.size
                    }
                    Status.ERROR -> {
                        resource.message?.let { it1 -> Log.d("😀", it1) }
                    }
                    Status.LOADING -> {
                        Log.d("😀","loading")

                    }
                }
            }
        })
    }

//    fun setupViewPager(){
//        binding.viewpager.adapter = MainFragmentPagerAdapter(
//            lifecycle = lifecycle,
//            fragmentManager = supportFragmentManager
//        )
//        binding.viewpager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
//            override fun onPageSelected(position: Int) {
//                if (position == 0) {
//                    binding.bottomNavigation.selectedItemId= R.id.news
//                }
//                else if (position == 1) {
//                    binding.bottomNavigation.selectedItemId= R.id.download
//                }
//
//                super.onPageSelected(position)
//            }
//        })
//    }
//
//
//    fun setupBottomNavigation(){
//        binding.bottomNavigation.selectedItemId= R.id.news
//        binding.bottomNavigation.setOnItemSelectedListener { item ->
//            when(item.itemId) {
//                R.id.news -> {
//                    binding.viewpager.setCurrentItem(0,true)
//                    // Respond to navigation item 1 click
//                    true
//                }
//                R.id.download -> {
//                    binding.viewpager.setCurrentItem(1,true)
//                    // Respond to navigation item 2 click
//                    true
//                }
//                else -> false
//            }
//        }
//    }
}