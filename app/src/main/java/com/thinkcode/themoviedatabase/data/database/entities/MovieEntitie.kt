package com.thinkcode.themoviedatabase.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movie_table")
data class MovieEntitie(
    @PrimaryKey
    @ColumnInfo(name="id") var id: Int,
    @ColumnInfo(name="original_title")var original_title: String,
    @ColumnInfo(name="poster_path")var poster_path: String,
    @ColumnInfo(name="genero") var genero:String,
    @ColumnInfo(name="original_language")var original_language:String,
    @ColumnInfo(name="overview") var overview:String,
    @ColumnInfo(name="popularity") var popularity:String,
    @ColumnInfo(name="release_date")var release_date:String

)
