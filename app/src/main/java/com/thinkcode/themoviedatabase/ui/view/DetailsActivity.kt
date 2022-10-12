package com.thinkcode.themoviedatabase.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.thinkcode.themoviedatabase.R
import com.thinkcode.themoviedatabase.core.Constants
import com.thinkcode.themoviedatabase.data.MovieRepository
import com.thinkcode.themoviedatabase.databinding.ActivityDetailsBinding
import com.thinkcode.themoviedatabase.ui.viewmodel.MovieViewModel

class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding
    private val movieViewModel: MovieViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val data = intent.getIntExtra("ID", 0)



        movieViewModel.getMovieDetailbyId(data)
        movieViewModel.detailsList.observe(this, Observer {

            val posterpath = Constants.PHOTO_BASE_URL + it.data!!.poster_path
            Glide.with(this).load(posterpath).into(binding.ivPortada)
            binding.tvTitle.text = it.data.original_title
            binding.idiomaOriginal.text = it.data.original_language
            val arrayGen = it.data.genres
            var genero = ""

            //Verificamos el array que posee los generos y lo guardamos.
            for (i in arrayGen) {
                Log.e("genero", i.name)
                genero = genero + " " + i.name
            }

            binding.tvGenero.text = genero
            binding.tvPopularity.text=it.data.popularity.toString()
            binding.tvOverview.movementMethod=ScrollingMovementMethod()
            binding.tvOverview.text = it.data.overview
            binding.tvReleaseDate.text = it.data.release_date

        })

    }

}