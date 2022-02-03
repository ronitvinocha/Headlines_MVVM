package com.practice.headlines.adapters
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.practice.headlines.R
import com.practice.headlines.clicklistener.RecyclerViewClickListner
import com.practice.headlines.model.Articles
import com.practice.headlines.model.DownloadStatus
import com.practice.headlines.viewholder.BaseViewHolder
import com.practice.headlines.viewholder.DownloadViewHolder
import com.practice.headlines.viewholder.NewsViewHolder
import com.squareup.picasso.Picasso
import java.lang.IllegalStateException
import kotlin.collections.ArrayList

class RecycleViewAdapter(val picasso: Picasso,val clickListner: RecyclerViewClickListner,val view:Int): RecyclerView.Adapter<BaseViewHolder>() {
    private val list = mutableListOf<Articles>()
    private var intialArticleList:ArrayList<Articles> =ArrayList()
    companion object{
        private const val NEWS_VIEW = R.layout.recycleitem
        private const val DOWNLOAS_VIEW=R.layout.recycleitemdownload
        private const val UNKNOWN_VIEW = -1
    }

    fun setData(list: List<Articles>) {
        this.list.clear()
        this.list.addAll(list)
        intialArticleList = ArrayList<Articles>().apply {
            list.let { addAll(it) }
        }
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
        holder.bind(data)
        if(view== NEWS_VIEW){
            val savebutton:Button=holder.itemView.findViewById(R.id.savebutton)
            val readbutton:Button=holder.itemView.findViewById(R.id.readbutton)
            if(data.downloaded!=DownloadStatus.DOWNLOAD){
                savebutton.setOnClickListener {
                    clickListner.onSaveClick(data, position)
                }
            }
            readbutton.setOnClickListener{
                clickListner.onReadClick(data)
            }
        }else{
            val removebutton:ImageButton=holder.itemView.findViewById(R.id.remove)
            holder.itemView.setOnClickListener {
                clickListner.onReadClick(data)
            }
            removebutton.setOnClickListener {
                clickListner.onRemoveClick(data,position)
            }
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

    fun getFilter(): Filter {
        return cityFilter
    }


    private val cityFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: ArrayList<Articles> = ArrayList()
            if (constraint == null || constraint.isEmpty()) {
                intialArticleList.let { filteredList.addAll(it) }
            } else {
                val query = constraint.toString().trim().toLowerCase()
                intialArticleList.forEach {article->
                    article.title?.let {
                        if(it.lowercase().contains(query)){
                            filteredList.add(article)
                        }
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results?.values is ArrayList<*>) {
                list.clear()
                list.addAll(results.values as ArrayList<Articles>)
                notifyDataSetChanged()
            }
        }
    }
}