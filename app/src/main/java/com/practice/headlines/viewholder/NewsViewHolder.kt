package com.practice.headlines.viewholder

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.practice.headlines.R
import com.practice.headlines.clicklistener.RecyclerViewClickListner
import com.practice.headlines.model.Articles
import com.practice.headlines.model.DownloadStatus
import com.practice.headlines.persistance.Article
import com.practice.headlines.util.gone
import com.practice.headlines.util.visible
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class NewsViewHolder(itemview: View,val picasso: Picasso) :BaseViewHolder(itemview) {
    val source: TextView
    val date: TextView
    val title: TextView
    val imageView: ImageView
    val downloadImageView:ImageView
    val loader:ProgressBar
    val itemLayout: ConstraintLayout

    init {
        source=itemView.findViewById(R.id.source)
        date=itemView.findViewById(R.id.date)
        title=itemView.findViewById(R.id.title)
        imageView=itemView.findViewById(R.id.image)
        itemLayout=itemView.findViewById(R.id.itemlayout)
        downloadImageView=itemview.findViewById(R.id.downloadimage)
        loader=itemview.findViewById(R.id.progressbar)
    }

    override  fun bind(data:Articles,clickListner: RecyclerViewClickListner) {
        Log.d("😄",data.toString())
        source.text=data.source?.name
        date.text=data.publishedAt
        title.text=data.title
        if(data.downloaded==DownloadStatus.DOWNLOAD){
            loader.gone()
            downloadImageView.visible()
            downloadImageView.setBackgroundResource(R.drawable.ic_baseline_check_circle_24)
        }else if(data.downloaded==DownloadStatus.LOADING){
            downloadImageView.gone()
            loader.visible()
        }else{
            loader.gone()
            downloadImageView.visible()
            downloadImageView.setBackgroundResource(R.drawable.ic_baseline_arrow_circle_down_24)
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