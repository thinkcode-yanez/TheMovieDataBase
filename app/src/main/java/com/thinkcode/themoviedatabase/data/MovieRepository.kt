package com.thinkcode.themoviedatabase.data


import com.thinkcode.themoviedatabase.data.database.dao.MovieDao
import com.thinkcode.themoviedatabase.data.database.entities.MovieEntitie
import com.thinkcode.themoviedatabase.data.network.ApiNetworkService
import javax.inject.Inject


class MovieRepository @Inject constructor(
    private val api: ApiNetworkService,
    private val db: MovieDao

) {

    //From API
    suspend fun getAllMovies(pageNumber: Int) =
        api.getPopularMovies(pageNumber = pageNumber)

    suspend fun getMovieById(movieid: Int) = api.getMovieById(movieid)
    suspend fun searchMovie(query: String) = api.searchMovie(query2 = query)
    suspend fun getToken() = api.getToken()
    suspend fun getSession(body: String) = api.getSessionId(body)
    suspend fun rateMovie(movie: Int, sessionid: String, value: Double) =
        api.rateMovie(movie, session_id = sessionid, value = value)


    //From ROOM DATABASE
    suspend fun insertMovieInDataBase(movie: MovieEntitie) =
        db.insertMovie(movie = movie)

    suspend fun getAllMovieFromDataBase() = db.getAllMovies()
    suspend fun getMovieByIdFromDatabase(id: Int) = db.getMovieByIdFromDataBase(id)


}