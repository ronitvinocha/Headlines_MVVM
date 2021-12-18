package com.practice.headlines.dependencies

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.practice.headlines.R
import com.practice.headlines.persistance.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {
    @Provides
    @Singleton
    fun providesSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.getString(R.string.shared_pref_key),Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideRoomDb(context: Context):RoomDatabase{
        return Room.databaseBuilder(context,AppDatabase::class.java,"app.db").build()
    }
}