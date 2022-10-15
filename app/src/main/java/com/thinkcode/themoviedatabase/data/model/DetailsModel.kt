package com.thinkcode.themoviedatabase.data.model

data class DetailsModel(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: Any,
    val budget: Int,
    val genres: List<GenreX>,
    val homepage: String,
    val id: Int,//Cambiar a int
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val revenue: Long,//Anteriormente estaba en INT y crasheaba con TITANIC Y AVATAR , not anymore
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)