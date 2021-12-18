package com.practice.headlines

import com.practice.headlines.dependencies.CoreComponent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import com.practice.headlines.repository.Repository
import com.practice.headlines.viewmodel.MainViewModel

class MyViewModelFactory(private val coreComponent: CoreComponent) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(Repository(coreComponent)) as T
    }
}