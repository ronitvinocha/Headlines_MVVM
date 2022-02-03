package com.practice.headlines.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.practice.headlines.clicklistener.RecyclerViewClickListner
import com.practice.headlines.model.Articles

abstract class BaseViewHolder(itemview:View): RecyclerView.ViewHolder(itemview) {
    abstract fun bind(data:Articles)
}