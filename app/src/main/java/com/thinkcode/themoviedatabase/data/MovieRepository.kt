package com.thinkcode.themoviedatabase.data

import com.thinkcode.themoviedatabase.core.RetrofitHelper
import com.thinkcode.themoviedatabase.data.network.ApiNetworkService

class MovieRepository {

    val retrofit = RetrofitHelper.getRetrofit()
    val api=retrofit.create(ApiNetworkService::class.java)

    suspend fun getAllMovies(language: String, pageNumber: Int) =

        //  api.getPopularMovies(language = language, pageNumber = pageNumber)
        api.getPopularMovies(pageNumber = pageNumber)//quitar pagenumber

    suspend fun getMovieById(movieid:Int) =
        api.getMovieById(movieid)



}