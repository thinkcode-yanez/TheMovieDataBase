package com.thinkcode.themoviedatabase.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.thinkcode.themoviedatabase.core.RetrofitHelper
import com.thinkcode.themoviedatabase.data.paging.MoviePagingSource

class PopularViewModel: ViewModel() {


    val listData= Pager(PagingConfig(1)){
            MoviePagingSource()
    }.flow.cachedIn(viewModelScope)
}