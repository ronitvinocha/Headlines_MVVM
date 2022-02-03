package com.practice.headlines.fragments

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.practice.headlines.R
import com.practice.headlines.clicklistener.RecyclerViewClickListner
import com.practice.headlines.databinding.FragmentNewsBinding

abstract class BaseFragment(val layoutId:Int): Fragment(),RecyclerViewClickListner {
    protected lateinit var recyclerView: RecyclerView
    protected lateinit var progressBar:ProgressBar
    protected lateinit var binding:FragmentNewsBinding
    protected lateinit var searchView: androidx.appcompat.widget.SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater,container,false)
        return binding.getRoot()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.rvMain
        progressBar=binding.progressbar
        searchView=binding.searchBar
        setupRecyclerView()
        setupSearchView()
    }

    abstract fun setupRecyclerView()
    abstract fun setupSearchView()
}