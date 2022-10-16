package com.thinkcode.themoviedatabase.core

import android.app.Application
import androidx.room.Room
import com.thinkcode.themoviedatabase.data.database.MovieDataBase
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MovieApp : Application() {

    companion object {
        lateinit var pref: Prefs

    }

    override fun onCreate() {
        super.onCreate()

       pref= Prefs(applicationContext)


    }
}