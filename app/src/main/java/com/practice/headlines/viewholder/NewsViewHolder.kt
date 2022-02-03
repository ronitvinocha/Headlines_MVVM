package com.practice.headlines.viewholder

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.practice.headlines.R
import com.practice.headlines.model.Articles
import com.practice.headlines.model.DownloadStatus
import com.practice.headlines.util.getParsedDateString
import com.practice.headlines.util.gone
import com.practice.headlines.util.visible
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class NewsViewHolder(itemview: View,val picasso: Picasso) :BaseViewHolder(itemview) {
    val date: TextView
    val title: TextView
    val description:TextView
    val imageView: ImageView
    val downloadImageView:ImageView
    val itemLayout: ConstraintLayout
    val read:Button
    val save:Button

    init {
        date=itemView.findViewById(R.id.date)
        title=itemView.findViewById(R.id.title)
        description=itemview.findViewById(R.id.description)
        imageView=itemView.findViewById(R.id.image)
        itemLayout=itemView.findViewById(R.id.itemlayout)
        downloadImageView=itemview.findViewById(R.id.downloadimage)
        read=itemview.findViewById(R.id.readbutton)
        save=itemview.findViewById(R.id.savebutton)
    }

    override  fun bind(data:Articles) {
        val format= SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH
        )
        val outputFormat= SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH
        )
        title.text=data.title
        data.description?.let {
            description.text=it
        }?: kotlin.run {
            description.gone()
        }
        data.publishedAt?.let {
            date.text= getParsedDateString(date = it)
        }?:run{
            date.gone()
        }
        if(data.downloaded==DownloadStatus.DOWNLOAD){
            downloadImageView.visible()
            downloadImageView.setBackgroundResource(R.drawable.savebutton)
            save.isEnabled=false
            save.setText("Saved")
        }else if(data.downloaded==DownloadStatus.LOADING){
            downloadImageView.gone()
        }else{
            downloadImageView.visible()
            downloadImageView.setBackgroundResource(R.drawable.ic_save_description)
            save.isEnabled=true
            save.setText("Save")
        }
        if(data.urlToImage!=null){
            picasso.load(data.urlToImage).fit().centerCrop().networkPolicy(
                NetworkPolicy.OFFLINE).into(imageView,object: Callback {
                override fun onSuccess() {
                    Log.d("piccaso","success ${data.urlToImage}")
                }
                override fun onError(e: java.lang.Exception?) {
                    Log.d("piccaso","failure ${data.urlToImage}")
                    picasso.load(data.urlToImage).fit().centerCrop().into(imageView)
                }
            })
        }
    }
}