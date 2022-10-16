package com.thinkcode.themoviedatabase.data.paging

import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thinkcode.themoviedatabase.data.MovieRepository
import com.thinkcode.themoviedatabase.data.model.Result
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val movierepo:MovieRepository

) : PagingSource<Int, Result>() {







    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {

        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {

        return try{
            val currentPage = params.key?:1
            val response= movierepo.getAllMovies(currentPage)
            val data= response.body()?.results?: emptyList()
            val responseData= mutableListOf<Result>()
            responseData.addAll(data)
            LoadResult.Page(
                data=responseData,
                prevKey = if(currentPage==1) null else -1,
                nextKey = currentPage.plus(1)
            )


        }catch (e:Exception){
            LoadResult.Error(e)

        }catch (e:IOException){
            LoadResult.Error(e)
        }
    }

}