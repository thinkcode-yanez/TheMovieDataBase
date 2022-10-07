package com.thinkcode.themoviedatabase.ui.view.adapter

import android.content.Context
import android.view.View
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.thinkcode.themoviedatabase.databinding.MovieItemBinding

const val BASE_URL="https://image.tmdb.org/t/p/original"

class MovieViewHolder(view: View):RecyclerView.ViewHolder(view){

    val binding = MovieItemBinding.bind(view)

    fun render(item:com.thinkcode.themoviedatabase.data.model.Result){
        binding.tvtitleMovie.text= item.original_title
        val posterpath= BASE_URL+item.poster_path
        Glide.with(binding.imageView.context).load(posterpath).into(binding.imageView)
    }
}