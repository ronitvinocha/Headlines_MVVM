package com.practice.headlines.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.headlines.MyViewModelFactory
import com.practice.headlines.Myapplication
import com.practice.headlines.R
import com.practice.headlines.adapters.RecycleViewAdapter
import com.practice.headlines.model.Articles
import com.practice.headlines.model.DownloadStatus
import com.practice.headlines.ui.DescriptionActivity
import com.practice.headlines.ui.DescriptionActivityArgs
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
 * Use the [DownloadsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DownloadsFragment : BaseFragment(R.layout.fragment_news) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private  val mainViewModel: MainViewModel by activityViewModels{ MyViewModelFactory(myApplication.coreComponent) }
    private lateinit var myApplication: Myapplication
    private lateinit var picasso: Picasso
    private lateinit var downloadAdapter: RecycleViewAdapter


    override fun onAttach(context: Context) {
        super.onAttach(context)
        myApplication=this.activity?.application as Myapplication
        picasso= myApplication.coreComponent.picaaso()
        downloadAdapter= RecycleViewAdapter(picasso,this,R.layout.recycleitemdownload)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.articlesFromDB.observe(viewLifecycleOwner, Observer {
            it?.let {resource->
                when(resource.status){
                    Status.SUCCESS -> {
                        progressBar.gone()
                        downloadAdapter.setData(resource.data as List<Articles>)
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
            adapter = downloadAdapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )

        }
    }

    override fun onClick(article: Articles,position:Int) {

    }

    override fun onRemoveClick(article: Articles, position: Int) {
        mainViewModel.deleteArticle(article).observe(viewLifecycleOwner, Observer {
            it?.let {resource->
                when(resource.status){
                    Status.SUCCESS -> {
                    }
                    Status.ERROR -> {
                        resource.message?.let { it1 -> Log.d("😀", it1) }
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    override fun onItemClick(article: Articles) {
        val action = DownloadsFragmentDirections.actionDownloadToDescriptionActivity(article.urlToImage!!,article.title!!,article.description!!,article.publishedAt!!,article.source?.name!!)
        findNavController(this).navigate(action)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DownloadsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            DownloadsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}