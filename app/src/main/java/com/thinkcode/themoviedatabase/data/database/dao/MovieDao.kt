package com.thinkcode.themoviedatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.thinkcode.themoviedatabase.data.database.entities.MovieEntitie


@Dao
interface MovieDao {

    @Insert
     suspend fun insertMovie(movie: MovieEntitie)

    @Query("SELECT * FROM movie_table")
    suspend fun getAllMovies(): List<MovieEntitie>

    @Query("SELECT * FROM movie_table where id=:id")
    suspend fun getMovieByIdFromDataBase(id:Int): List<MovieEntitie>//cambiar a lista

}