package com.practice.headlines.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practice.headlines.R
import com.practice.headlines.model.Articles
import com.practice.headlines.persistance.Article
import com.practice.headlines.viewholder.BaseViewHolder
import com.practice.headlines.viewholder.NewsViewHolder
import com.squareup.picasso.Picasso
import java.lang.IllegalStateException

class RecycleViewAdapter(val picasso: Picasso): RecyclerView.Adapter<BaseViewHolder>() {
    private val list = mutableListOf<Articles>()
    companion object{
        private const val NEWS_VIEW = R.layout.recycleitem
        private const val UNKNOWN_VIEW = -1
    }

    fun setData(list: List<Articles>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when(viewType){
            NEWS_VIEW->{
                val v: View = LayoutInflater.from(parent.context)
                    .inflate(NEWS_VIEW, parent, false)
                NewsViewHolder(v,picasso)
            }
            else->{
                throw IllegalStateException("Invalid view")
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val data=list[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return NEWS_VIEW
    }
}