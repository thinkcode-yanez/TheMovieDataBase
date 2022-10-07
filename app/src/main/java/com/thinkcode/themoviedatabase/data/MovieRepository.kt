package com.thinkcode.themoviedatabase.data

import com.thinkcode.themoviedatabase.core.RetrofitHelper
import com.thinkcode.themoviedatabase.data.model.TMDBModel
import com.thinkcode.themoviedatabase.data.network.ApiNetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object MovieRepository {

    val retrofit=RetrofitHelper.getRetrofit()

    suspend fun getAllMovies():TMDBModel?{
        return withContext(Dispatchers.IO) {


                val response = retrofit.create(ApiNetworkService::class.java).getPopularMovies()
                response.body()


        }
    }
}