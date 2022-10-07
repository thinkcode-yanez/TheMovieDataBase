package com.thinkcode.themoviedatabase.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thinkcode.themoviedatabase.data.MovieRepository
import com.thinkcode.themoviedatabase.data.model.TMDBModel
import kotlinx.coroutines.launch

class MovieViewModel:ViewModel() {

    val mutableList=MutableLiveData<TMDBModel?>()
    var progressbar=MutableLiveData<Boolean>()

    fun getAllPopularMovies(){

        viewModelScope.launch {
            progressbar.value=true
            val newlist= MovieRepository.getAllMovies()
            mutableList.postValue(newlist)
            progressbar.value=false
        }

    }
}