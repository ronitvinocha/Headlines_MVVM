package com.practice.headlines.viewholder

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.practice.headlines.R
import com.practice.headlines.model.Articles
import com.practice.headlines.util.getParsedDateString
import com.practice.headlines.util.gone
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.util.*

class DownloadViewHolder(itemview: View, val picasso: Picasso) :BaseViewHolder(itemview) {
    val source: TextView
    val date: TextView
    val title: TextView
    val imageView: ImageView

    init {
        source=itemView.findViewById(R.id.source)
        date=itemView.findViewById(R.id.date)
        title=itemView.findViewById(R.id.title)
        imageView=itemView.findViewById(R.id.image)
    }

    override fun bind(data: Articles) {
        Log.d("🥶",data.toString())
        source.text=data.source?.name
        date.text=data.publishedAt
        title.text=data.title
        data.publishedAt?.let {
            date.text= getParsedDateString(it)
        }?:run{
            date.gone()
        }
        if(data.urlToImage!=null){
            picasso.load(data.urlToImage).fit().centerCrop().networkPolicy(
                NetworkPolicy.OFFLINE).into(imageView,object: Callback {
                override fun onSuccess() {

                }

                override fun onError(e: java.lang.Exception?) {
                    println(e?.message)
                    picasso.load(data.urlToImage).fit().centerCrop().into(imageView)
                }
            })
        }
    }
}