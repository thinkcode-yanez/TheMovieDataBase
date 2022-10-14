package com.thinkcode.themoviedatabase.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thinkcode.themoviedatabase.data.database.dao.MovieDao
import com.thinkcode.themoviedatabase.data.database.entities.MovieEntitie


@Database(entities = [MovieEntitie::class], version = 1 )
abstract class MovieDataBase:RoomDatabase() {

    abstract fun getMovieDao():MovieDao

}