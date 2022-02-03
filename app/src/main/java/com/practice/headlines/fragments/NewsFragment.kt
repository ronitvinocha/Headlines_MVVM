package com.practice.headlines.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.headlines.MyViewModelFactory
import com.practice.headlines.Myapplication
import com.practice.headlines.R
import com.practice.headlines.adapters.RecycleViewAdapter
import com.practice.headlines.model.Articles
import com.practice.headlines.model.DownloadStatus
import com.practice.headlines.persistance.Article
import com.practice.headlines.util.Status
import com.practice.headlines.util.gone
import com.practice.headlines.util.visible
import com.practice.headlines.viewmodel.MainViewModel
import com.squareup.picasso.Picasso

class NewsFragment : BaseFragment(R.layout.fragment_news) {
    private  val mainViewModel:MainViewModel by activityViewModels{ MyViewModelFactory(myApplication.coreComponent) }
    private lateinit var myApplication:Myapplication
    private lateinit var picasso: Picasso
    private lateinit var newsadapter:RecycleViewAdapter


    override fun onAttach(context: Context) {
        super.onAttach(context)
        myApplication=this.activity?.application as Myapplication
        picasso= myApplication.coreComponent.picaaso()
        newsadapter= RecycleViewAdapter(picasso,this,R.layout.recycleitem)
    }

    override fun onSaveClick(article: Articles, position:Int) {
        mainViewModel.saveArticle(article).observe(viewLifecycleOwner, Observer {
            it?.let {resource->
                when(resource.status){
                    Status.SUCCESS -> {
                        article.downloaded=DownloadStatus.DOWNLOAD
                        newsadapter.changeDataAtIndex(article,position)
                    }
                    Status.ERROR -> {
                        resource.message?.let { it1 -> Log.d("😀", it1) }
                        article.downloaded=DownloadStatus.NDTDOWNLOAD
                        newsadapter.changeDataAtIndex(article,position)
                    }
                    Status.LOADING -> {
                        article.downloaded=DownloadStatus.LOADING
                        newsadapter.changeDataAtIndex(article,position)
                    }
                }
            }
        })
    }

    override fun onRemoveClick(article: Articles, position: Int) {

    }

    override fun onReadClick(article: Articles) {
        Log.d("🥶","here")
        val action = NewsFragmentDirections.actionNewsToDescriptionActivity(article)
        NavHostFragment.findNavController(this).navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.topHeadlines.observe(viewLifecycleOwner, Observer {
            it?.let {resource->
                when(resource.status){
                    Status.SUCCESS -> {
                        Log.d("🤬","newsfragmentlates")
                        progressBar.gone()
                        resource.data?.let {
                                it1 -> newsadapter.setData(it1)
                        }
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

    override fun setupRecyclerView() {
        recyclerView.visible()
        recyclerView.apply {
            adapter = newsadapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    override fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val adapter=recyclerView.adapter as RecycleViewAdapter
                adapter.getFilter().filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("😇",newText?:"")
                val adapter=recyclerView.adapter as RecycleViewAdapter
                adapter.getFilter().filter(newText)
                return true
            }

        })
    }


    companion object {
        @JvmStatic
        fun newInstance() = NewsFragment()
    }
}