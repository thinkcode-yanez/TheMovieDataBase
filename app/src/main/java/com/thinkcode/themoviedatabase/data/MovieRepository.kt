package com.thinkcode.themoviedatabase.data

import com.thinkcode.themoviedatabase.core.RetrofitHelper
import com.thinkcode.themoviedatabase.data.network.ApiNetworkService

class MovieRepository {

    val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getAllMovies(language: String, pageNumber: Int) =
        retrofit.create(ApiNetworkService::class.java)
            .getPopularMovies(language = language, pageNumber = pageNumber)


}