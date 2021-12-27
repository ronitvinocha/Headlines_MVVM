package com.practice.headlines.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.practice.headlines.dependencies.CoreComponent
import com.practice.headlines.model.NewsApiResponse
import com.practice.headlines.network.Apis
import com.practice.headlines.persistance.Article
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.flow.Flow
import rx.Observer

class Repository (private  val coreComponent: CoreComponent){

    suspend fun getTopHeadlines():NewsApiResponse{
       return coreComponent.retrofit().create(Apis::class.java).getTopHeadlines("US","business",1,"7851dde50bc345b4979c5b6dcec07f7e")
    }

    suspend fun addArticle(article: Article):Boolean{
        return try {
            val list=coreComponent.roomDB().NewsDescriptionDao().insertArticle(article)
            Log.d("☺️",list.toString())
            true
        }catch (exception:Exception){
            throw exception
        }
    }

    suspend fun deleteArticle(id: Int):Boolean{
        return try {
            val list=coreComponent.roomDB().NewsDescriptionDao().delete(id)
            Log.d("☺️",list.toString())
            true
        }catch (exception:Exception){
            throw exception
        }
    }

    fun getArticlefromdb():Flow<List<Article>>{
        return try {
            coreComponent.roomDB().NewsDescriptionDao().getArticles()
        }catch (exception:Exception){
            throw exception
        }
    }
}