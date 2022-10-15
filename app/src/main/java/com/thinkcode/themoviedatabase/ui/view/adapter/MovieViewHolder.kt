package com.thinkcode.themoviedatabase.ui.view.adapter

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.view.View
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.thinkcode.themoviedatabase.core.Constants
import com.thinkcode.themoviedatabase.databinding.MovieItemBinding
import com.thinkcode.themoviedatabase.ui.view.DetailsActivity
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context


class MovieViewHolder(view: View):RecyclerView.ViewHolder(view){

    private val binding = MovieItemBinding.bind(view)
    private val context=view.context

    fun render(item:com.thinkcode.themoviedatabase.data.model.Result){
        binding.tvtitleMovie.text= item.original_title

        val posterpath= Constants.PHOTO_BASE_URL+item.poster_path
        Glide.with(binding.imageView.context).load(posterpath).into(binding.imageView)
        binding.imageView.setOnClickListener {
            val intent= Intent(context,DetailsActivity::class.java)
            intent.putExtra("ID",item.id)
            //intent.putExtra("ide","45")
            context.startActivity(intent)
        }

    }
}