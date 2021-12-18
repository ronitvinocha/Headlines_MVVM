package com.practice.headlines.network

import com.practice.headlines.model.NewsApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable
import java.util.*

interface Apis {
    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country:String,@Query("category")category:String,@Query("page")page:Int,@Query("apiKey")apiKey:String):NewsApiResponse
}