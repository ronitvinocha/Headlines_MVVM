package com.practice.headlines.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.practice.headlines.R
import com.practice.headlines.clicklistener.RecyclerViewClickListner

abstract class BaseFragment(val layoutId:Int): Fragment(),RecyclerViewClickListner {
    protected lateinit var recyclerView: RecyclerView
    protected lateinit var progressBar:ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutId, container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rvMain)
        progressBar=view.findViewById(R.id.progressbar)
        setupRecyclerView()
    }

    abstract fun setupRecyclerView()
}