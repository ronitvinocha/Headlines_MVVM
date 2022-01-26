package com.practice.headlines.adapters
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.practice.headlines.R
import com.practice.headlines.clicklistener.RecyclerViewClickListner
import com.practice.headlines.model.Articles
import com.practice.headlines.model.DownloadStatus
import com.practice.headlines.persistance.Article
import com.practice.headlines.viewholder.BaseViewHolder
import com.practice.headlines.viewholder.DownloadViewHolder
import com.practice.headlines.viewholder.NewsViewHolder
import com.squareup.picasso.Picasso
import java.lang.IllegalStateException

class RecycleViewAdapter(val picasso: Picasso,val clickListner: RecyclerViewClickListner,val view:Int): RecyclerView.Adapter<BaseViewHolder>() {
    private val list = mutableListOf<Articles>()
    companion object{
        private const val NEWS_VIEW = R.layout.recycleitem
        private const val DOWNLOAS_VIEW=R.layout.recycleitemdownload
        private const val UNKNOWN_VIEW = -1
    }

    fun setData(list: List<Articles>) {
        Log.d("😘",list.toString())
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun changeDataAtIndex(articles: Articles,position: Int){
        this.list.set(position,articles)
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when(viewType){
            NEWS_VIEW->{
                val v: View = LayoutInflater.from(parent.context)
                    .inflate(NEWS_VIEW, parent, false)
                NewsViewHolder(v,picasso)
            }
            DOWNLOAS_VIEW->{
                val v: View = LayoutInflater.from(parent.context)
                    .inflate(DOWNLOAS_VIEW, parent, false)
                DownloadViewHolder(v,picasso)
            }
            else->{
                throw IllegalStateException("Invalid view")
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val data=list[position]
        holder.bind(data,clickListner)
        val downloadImageView:ImageView=holder.itemView.findViewById(R.id.downloadimage)
        if(view== NEWS_VIEW){
            if(data.downloaded!=DownloadStatus.DOWNLOAD){
                downloadImageView.setOnClickListener {
                    clickListner.onClick(data, position)
                }
            }
        }else{
            downloadImageView.setOnClickListener {
                clickListner.onRemoveClick(data,position)
            }
        }
        holder.itemView.setOnClickListener {
            clickListner.onItemClick(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(view){
            NEWS_VIEW-> NEWS_VIEW
            DOWNLOAS_VIEW-> DOWNLOAS_VIEW
            else->throw IllegalStateException("Invalid view")
        }
    }
}