package com.thinkcode.themoviedatabase.data.network

import com.thinkcode.themoviedatabase.core.Constants
import com.thinkcode.themoviedatabase.data.model.DetailsModel
import com.thinkcode.themoviedatabase.data.model.TMDBModel
import com.thinkcode.themoviedatabase.data.model.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiNetworkService {

    // @GET("popular?api_key=24ac229d39c79b4f0f4ea65264aefc24&language=en-US&page=1")
    @GET("movie/popular?")
    suspend fun getPopularMovies(
        @Query("api_key")
        apiKey: String = Constants.API_KEY,
        @Query("language")
        language: String = "en-US",
        @Query("page")
        pageNumber: Int = 1
    ): Response<TMDBModel>


    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id")
        movie_id: Int,
        @Query("api_key")
        apiKey: String = Constants.API_KEY,
        @Query("language")
        language: String = "en-US",
    ): Response<DetailsModel>//Result

    //https://api.themoviedb.org/3/search
    // /movie?api_key=24ac229d39c79b4f0f4ea65264aefc24&language=en-US&query=HOLA&page=1&include_adult=false

    //https://api.themoviedb.org/3/
    // search/movie?api_key=24ac229d39c79b4f0f4ea65264aefc24&query=hocus

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key")
        apiKey: String = Constants.API_KEY,
        @Query("query")
        query2:String="",
    ): Response<TMDBModel>

}