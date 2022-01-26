package com.practice.headlines.clicklistener

import com.practice.headlines.model.Articles

interface RecyclerViewClickListner {
    fun onClick(article: Articles,position:Int)
    fun onRemoveClick(article: Articles,position: Int)
    fun onItemClick(article: Articles)
}