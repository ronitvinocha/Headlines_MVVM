package com.practice.headlines.dependencies

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ImageModule {
    @Provides
    @Singleton
    fun providePiccaso(context: Context,okHttp3Downloader:OkHttp3Downloader): Picasso {
        return Picasso.Builder(context)
            .downloader(okHttp3Downloader)
            .build()
    }

    @Provides
    @Singleton
    fun providesokhttpclient(context: Context):OkHttp3Downloader{
        return OkHttp3Downloader(context)
    }
}