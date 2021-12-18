package com.practice.headlines.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.practice.headlines.dependencies.CoreComponent
import com.practice.headlines.model.NewsApiResponse
import com.practice.headlines.network.Apis
import com.practice.headlines.persistance.Article
import io.reactivex.android.schedulers.AndroidSchedulers
import rx.Observer

class Repository (private  val coreComponent: CoreComponent){

    suspend fun getTopHeadlines():NewsApiResponse{
       return coreComponent.retrofit().create(Apis::class.java).getTopHeadlines("US","business",1,"7851dde50bc345b4979c5b6dcec07f7e")
    }
}