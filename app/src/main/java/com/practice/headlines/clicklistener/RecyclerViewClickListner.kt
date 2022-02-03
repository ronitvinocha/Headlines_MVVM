package com.practice.headlines.clicklistener

import com.practice.headlines.model.Articles

interface RecyclerViewClickListner {
    fun onSaveClick(article: Articles, position:Int)
    fun onRemoveClick(article: Articles,position: Int)
    fun onReadClick(article: Articles)
}