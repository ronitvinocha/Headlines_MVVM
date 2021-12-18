package com.practice.headlines.dependencies

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(gsonConverterFactory: GsonConverterFactory,
                         rxJavaCallAdapterFactory: RxJavaCallAdapterFactory,
                         okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesGGsonConverterFactory():GsonConverterFactory{
        return  GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesrxJavaCallAdapterFactory():RxJavaCallAdapterFactory{
        return RxJavaCallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(cache: Cache):OkHttpClient{
        val client = OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
        return client.build()
    }

    @Provides
    @Singleton
    fun providesOkhttpCache(context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        return Cache(context.cacheDir, cacheSize.toLong())
    }

}