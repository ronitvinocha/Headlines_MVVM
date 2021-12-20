package com.practice.headlines.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.practice.headlines.dependencies.CoreComponent
import com.practice.headlines.repository.Repository
import com.practice.headlines.util.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(val repository: Repository) :ViewModel() {
    fun getTopHeadlines() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getTopHeadlines()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}