package com.thinkcode.themoviedatabase.core

import android.app.Application
import androidx.room.Room
import com.thinkcode.themoviedatabase.data.database.MovieDataBase


class MovieApp : Application() {

    companion object {
        lateinit var db: MovieDataBase
        lateinit var prefs: Prefs

    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            this,
            MovieDataBase::class.java,
            "moviesdb"
            ).build()

         prefs= Prefs(applicationContext)


    }
}