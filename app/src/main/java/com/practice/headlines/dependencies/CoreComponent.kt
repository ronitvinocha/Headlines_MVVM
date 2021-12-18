package com.practice.headlines.dependencies

import android.content.SharedPreferences
import androidx.room.RoomDatabase
import com.squareup.picasso.Picasso
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ImageModule::class,NetworkModule::class,StorageModule::class,AppModule::class]
)

interface CoreComponent {
    fun retrofit():Retrofit
    fun sharedPrefrence():SharedPreferences
    fun roomDB():RoomDatabase
    fun picaaso():Picasso
}