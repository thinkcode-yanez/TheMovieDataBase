package com.thinkcode.themoviedatabase.data.network

import com.thinkcode.themoviedatabase.data.model.TMDBModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiNetworkService {


    @GET("popular?api_key=24ac229d39c79b4f0f4ea65264aefc24&language=en-US&page=1")
    suspend fun getPopularMovies():Response<TMDBModel>
}