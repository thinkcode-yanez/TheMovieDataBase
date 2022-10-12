package com.thinkcode.themoviedatabase.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thinkcode.themoviedatabase.core.Resource
import com.thinkcode.themoviedatabase.data.MovieRepository
import com.thinkcode.themoviedatabase.data.model.DetailsModel
import com.thinkcode.themoviedatabase.data.model.Result
import com.thinkcode.themoviedatabase.data.model.TMDBModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MovieViewModel : ViewModel() {

    val pupularList: MutableLiveData<Resource<TMDBModel>> = MutableLiveData()
    val detailsList: MutableLiveData<Resource<DetailsModel>> =
        MutableLiveData()
    //val generoList :MutableLiveData<Resource<GenerModel>> = MutableLiveData()
    var progressbar = MutableLiveData<Boolean>()
    val nolista = MutableLiveData<Boolean>()
    val movierepo = MovieRepository()

    var page = 1


    init {
        getAllPopularMovies("en-US", page)//quitar el page test
    }


    fun getAllPopularMovies(language: String,page:Int) {

        viewModelScope.launch {

            try {
                progressbar.value = true
                val response = movierepo.getAllMovies(language, page)
                progressbar.value = false
                pupularList.postValue(handleResponse(response))

            } catch (e: IOException) {
                e.message?.let { Log.e("error", it) }
                nolista.postValue(false)
            }

        }

    }

    fun getMovieDetailbyId(movieid: Int) {
        viewModelScope.launch {
            try {
                progressbar.value = true
                //   val response = movierepo.getAllMovies(language, page)
                val response = movierepo.getMovieById(movieid)
                progressbar.value = false
               // detailsList.postValue(handleResponse2(response))
                detailsList.postValue(handleSearchResponse(response))

            } catch (e: IOException) {
                e.message?.let { Log.e("error", it) }
                nolista.postValue(false)
            }
        }
    }
   /* fun getGeneros(){
        viewModelScope.launch {
            val response = movierepo.getGeneroMovie()
            generoList.postValue(handleGeneroResponse(response))
        }
    }*/


    //HANDLE RESPONSE METHODS
    private fun handleResponse(response: Response<TMDBModel>): Resource<TMDBModel> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())

    }

    private fun handleSearchResponse(response: Response<DetailsModel>): Resource<DetailsModel> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())

    }
    /*private fun handleGeneroResponse(response: Response<GenerModel>): Resource<GenerModel> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())

    }*/


}