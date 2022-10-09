package com.thinkcode.themoviedatabase.data.network

import com.thinkcode.themoviedatabase.core.Constants
import com.thinkcode.themoviedatabase.data.model.TMDBModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiNetworkService {

   // @GET("popular?api_key=24ac229d39c79b4f0f4ea65264aefc24&language=en-US&page=1")
    @GET("popular?")
    suspend fun getPopularMovies(
        @Query("api_key")
        apiKey: String = Constants.API_KEY,
        @Query("language")
        language: String = "en-US",
        @Query("page")
        pageNumber: Int = 1
    ): Response<TMDBModel>

}