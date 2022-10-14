package com.thinkcode.themoviedatabase.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.thinkcode.themoviedatabase.core.Resource
import com.thinkcode.themoviedatabase.data.MovieRepository
import com.thinkcode.themoviedatabase.data.database.entities.MovieEntitie
import com.thinkcode.themoviedatabase.data.model.DetailsModel
import com.thinkcode.themoviedatabase.data.model.Result
import com.thinkcode.themoviedatabase.data.model.TMDBModel
import com.thinkcode.themoviedatabase.data.paging.MoviePagingSource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MovieViewModel : ViewModel() {

    val pupularList: MutableLiveData<Resource<TMDBModel>> = MutableLiveData()
    val detailsList: MutableLiveData<Resource<DetailsModel>> =
        MutableLiveData()
    var progressbar = MutableLiveData<Boolean>()
    val nolista = MutableLiveData<Boolean>()
    val movierepo = MovieRepository()
    val responseFromDatabase=MutableLiveData<List<MovieEntitie>>()


    //TEST
    val visitada=MutableLiveData<Boolean>()


    //Lista para el Paginado. Pagin 3.0 Jetpack
    val listData= Pager(PagingConfig(1)){
        MoviePagingSource()
    }.flow.cachedIn(viewModelScope)






    //GET DATA FROM API
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
    fun searhMovie(query:String){
        viewModelScope.launch {

            try {
                progressbar.value = true
                val response = movierepo.searchMovie(query)
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
                progressbar.value = true //cambiar a progressbar.value = true
                val response = movierepo.getMovieById(movieid)
                progressbar.value = false
                detailsList.postValue(handleSearchResponse(response))

            } catch (e: IOException) {
                e.message?.let { Log.e("error", it) }
                nolista.postValue(false)
                progressbar.postValue(false)
            } catch (e:Exception){
                e.message?.let { Log.e("Formato Error", it) }
                progressbar.postValue(false)
            }
        }
    }


    //ROOM DATABASE INSERT/GETMOVIESBYID

    fun insertMovieInDatabase(movie:MovieEntitie){
        viewModelScope.launch{
            try {
                val result = movierepo.insertMovieInDataBase(movie)

            } catch (e: IOException) {
                e.message?.let { Log.e("error", it) }
            }
        }
    }

    fun getMovieByIdFromDatabase(int: Int)=
        viewModelScope.launch {
            try {
                val response=movierepo.getMovieByIdFromDatabase(int)
                if(response.isNullOrEmpty()) {
                    visitada.postValue(false)
                }else{
                    visitada.postValue(true)
                    responseFromDatabase.postValue(response)
                }
            } catch (e: IOException) {
                e.message?.let { Log.e("error", it) }
               // nolista.postValue(false)
               // visitada.postValue(false)
            } catch (e:Exception){
                e.message?.let { Log.e("Formato Error", it) }
             //   visitada.postValue(false)
            }
        }







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


}