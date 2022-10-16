package com.thinkcode.themoviedatabase.di

import android.content.Context
import androidx.room.Room
import com.thinkcode.themoviedatabase.data.database.MovieDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    const val MOVIE_DATABASE_NAME="moviesdb"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context,MovieDataBase::class.java, MOVIE_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDao(db:MovieDataBase)=db.getMovieDao()
}