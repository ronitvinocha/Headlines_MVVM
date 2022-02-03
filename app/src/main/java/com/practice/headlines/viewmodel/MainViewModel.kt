package com.practice.headlines.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.practice.headlines.dependencies.CoreComponent
import com.practice.headlines.model.Articles
import com.practice.headlines.model.DownloadStatus
import com.practice.headlines.model.Source
import com.practice.headlines.persistance.Article
import com.practice.headlines.repository.Repository
import com.practice.headlines.util.Resource
import com.practice.headlines.util.getParsedDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class MainViewModel(val repository: Repository) :ViewModel() {
    val topHeadlines = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            repository.getArticlefromdb().collect {
                val dbarticles:List<Articles> = it.map{ Articles(Source(it.sourceId,it.sourceName),it.author,it.title,it.description,it.url,it.urlToImage,it.publishedAt,it.content, downloaded = DownloadStatus.DOWNLOAD,id=it.id)}
                val articles:List<Articles> = repository.getTopHeadlines().articles
                articles.map {article->
                    val match=dbarticles.firstOrNull {dbarticle-> (article.author==dbarticle.author && article.title==dbarticle.title) }
                    if(match!=null){
                        article.downloaded=DownloadStatus.DOWNLOAD
                        article.id=match.id
                    }
                }
                emit(Resource.success(data = articles))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun saveArticle(article: Articles) = liveData(Dispatchers.IO) {
        val dbarticle= Article(article.source?.id,article.source?.name,article.author,article.title,article.description,article.url,article.urlToImage,article.publishedAt,article.content)
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.addArticle(dbarticle)))
        }catch (exception: Exception){
            emit(exception.message?.let { Resource.error(data = false, message = it) })
        }
    }

    fun deleteArticle(article: Articles) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.deleteArticle(article.id)))
        }catch (exception: Exception){
            emit(exception.message?.let { Resource.error(data = false, message = it) })
        }
    }

    val articlesFromDB=liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            repository.getArticlefromdb().collect {
                val articles:List<Articles> = it.map{ Articles(Source(it.sourceId,it.sourceName),it.author,it.title,it.description,it.url,it.urlToImage,it.publishedAt,it.content, downloaded = DownloadStatus.DOWNLOAD,id=it.id)}
                val comparator= Comparator{first:Articles,second:Articles->if(getParsedDate(first.publishedAt!!).after(getParsedDate(second.publishedAt!!)))-1 else 1 }
                val sortedArticles=articles.sortedWith(comparator)
                emit(Resource.success(data = sortedArticles))
            }
        }catch (exception: Exception){
            emit(exception.message?.let { Resource.error(data = false, message = it) })
        }
    }
}