package com.practice.headlines

import android.app.Application
import com.practice.headlines.dependencies.AppModule
import com.practice.headlines.dependencies.CoreComponent
import com.practice.headlines.dependencies.DaggerCoreComponent

class Myapplication:Application() {
    val coreComponent:CoreComponent by lazy {
        DaggerCoreComponent.builder()
            .appModule(AppModule(this)).
                build()
    }
}