package com.practice.headlines.ui

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.practice.headlines.Myapplication
import com.practice.headlines.dependencies.CoreComponent
import com.practice.headlines.R
import com.practice.headlines.databinding.ActivityMainBinding
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
        setSupportActionBar(binding.myToolbar)
        supportActionBar?.title=null
        myapplication=this.application as Myapplication
        coreComponent=myapplication.coreComponent
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return
        val navController = host.navController
        setupsavebutton(navController)

        val config= AppBarConfiguration(navGraph = navController.graph)
        binding.myToolbar.setupWithNavController(navController,config)
//        setupBottomNavigation(navController)
//        val mainViewModel: MainViewModel by viewModels{MyViewModelFactory(coreComponent)}
//        mainViewModel.articlesFromDB.observe(this, Observer{
//            it?.let {resource->
//                when(resource.status){
//                    Status.SUCCESS -> {
//                        val badge = binding.bottomNavigation.getOrCreateBadge(R.id.download)
//                        badge.isVisible = true
//                        val articles:List<Articles> = resource.data as List<Articles>
//                        Log.d("🤡",articles.size.toString())
//                        badge.number = articles.size
//                    }
//                    Status.ERROR -> {
//                        resource.message?.let { it1 -> Log.d("😀", it1) }
//                    }
//                    Status.LOADING -> {
//                        Log.d("😀","loading")
//
//                    }
//                }
//            }
//        })
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main_menu,menu)
//        return true
//    }


    fun setupsavebutton(navController:NavController){
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
        binding.addBtn.setOnClickListener {
            navController.navigate(R.id.download, null, optionsdownload)
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }
            Log.d("NavigationActivity", "Navigated to $dest")
            if(destination.id==R.id.news){
                binding.myToolbar.visible()
                binding.newslogi.visible()
                binding.addBtn.visible()
            }else if(destination.id==R.id.download){
                binding.myToolbar.visible()
                binding.newslogi.gone()
                binding.addBtn.gone()
            }else if(destination.id==R.id.descriptionFragment){
                binding.myToolbar.gone()
            }
        }
    }
}