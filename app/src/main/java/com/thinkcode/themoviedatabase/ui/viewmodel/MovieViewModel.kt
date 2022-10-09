package com.thinkcode.themoviedatabase.ui.viewmodel

import android.accounts.NetworkErrorException
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thinkcode.themoviedatabase.core.Resource
import com.thinkcode.themoviedatabase.core.RetrofitHelper
import com.thinkcode.themoviedatabase.data.MovieRepository
import com.thinkcode.themoviedatabase.data.model.TMDBModel
import com.thinkcode.themoviedatabase.data.network.ApiNetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

class MovieViewModel : ViewModel() {

    val mutableList: MutableLiveData<Resource<TMDBModel>> = MutableLiveData()
    var progressbar = MutableLiveData<Boolean>()
    val nolista = MutableLiveData<Boolean>()
    val movierepo = MovieRepository()

    var page = 1


    init {
        getAllPopularMovies("en-US")
    }


    fun getAllPopularMovies(language: String) {

        viewModelScope.launch {

            try {
                progressbar.value = true
                val response = movierepo.getAllMovies(language, page)
                progressbar.value = false
                mutableList.postValue(handleResponse(response))

            } catch (e: IOException) {
                e.message?.let { Log.e("error", it) }
                nolista.postValue(false)
            }

        }

    }

    private fun handleResponse(response: Response<TMDBModel>): Resource<TMDBModel> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())

    }
}