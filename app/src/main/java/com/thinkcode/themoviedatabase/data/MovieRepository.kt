package com.thinkcode.themoviedatabase.data

import com.thinkcode.themoviedatabase.core.MovieApp.Companion.db
import com.thinkcode.themoviedatabase.core.RetrofitHelper
import com.thinkcode.themoviedatabase.data.database.entities.MovieEntitie
import com.thinkcode.themoviedatabase.data.network.ApiNetworkService

class MovieRepository {

    val retrofit = RetrofitHelper.getRetrofit()
    val api = retrofit.create(ApiNetworkService::class.java)



    //From API
    suspend fun getAllMovies(language: String, pageNumber: Int) =
        api.getPopularMovies(pageNumber = pageNumber)
    suspend fun getMovieById(movieid: Int) = api.getMovieById(movieid)
    suspend fun searchMovie(query: String) = api.searchMovie(query2 = query)
    suspend fun getToken()=api.getToken()
    suspend fun getSession(body: String)=api.getSessionId(body)
    suspend fun rateMovie(movie:Int,sessionid:String,value:Double)=api.rateMovie(movie, session_id = sessionid, value = value)


    //From ROOM DATABASE
    suspend fun insertMovieInDataBase(movie: MovieEntitie) =
        db.getMovieDao().insertMovie(movie = movie)
    suspend fun getAllMovieFromDataBase() = db.getMovieDao().getAllMovies()
    suspend fun getMovieByIdFromDatabase(id: Int) = db.getMovieDao().getMovieByIdFromDataBase(id)





}