package com.practice.headlines.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragment : BaseFragment(R.layout.fragment_news) {
    private var param1: String? = null
    private var param2: String? = null
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

    override fun onClick(article: Articles,position:Int) {

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

    override fun onItemClick(article: Articles) {
        val action = NewsFragmentDirections.actionNewsToDescriptionActivity(article.urlToImage!!,article.title!!,article.description!!,article.publishedAt!!,article.source?.name!!)
        NavHostFragment.findNavController(this).navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.topHeadlines.observe(viewLifecycleOwner, Observer {
            it?.let {resource->
                when(resource.status){
                    Status.SUCCESS -> {
                        progressBar.gone()
                        resource.data?.let {
                                it1 -> newsadapter.setData(it1)
                        }
                        Log.d("😀",resource.data.toString())
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            NewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}