package com.thinkcode.themoviedatabase.ui.view

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.thinkcode.themoviedatabase.R
import com.thinkcode.themoviedatabase.core.Constants
import com.thinkcode.themoviedatabase.data.MovieRepository
import com.thinkcode.themoviedatabase.data.database.entities.MovieEntitie
import com.thinkcode.themoviedatabase.data.paging.MoviePagingSource
import com.thinkcode.themoviedatabase.databinding.ActivityDetailsBinding
import com.thinkcode.themoviedatabase.ui.viewmodel.MovieViewModel
import kotlinx.coroutines.*

class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding
    private val movieViewModel: MovieViewModel by viewModels()

    var title1 = ""
    var language1 = ""
    var popularidad = ""
    var overview = ""
    var date = ""
    var genero = ""
    var posterpath = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val data = intent.getIntExtra("ID", 0)

        movieViewModel.getMovieByIdFromDatabase(data)

        movieViewModel.visitada.observe(this, Observer {
            if (!it) {
                movieViewModel.getMovieDetailbyId(data)
                Toast.makeText(this, "Check it out", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Welcome Back", Toast.LENGTH_LONG).show()
               // setDataFromDatabase(data)
            }
        })


        movieViewModel.detailsList.observe(this, Observer {

            if (it.data?.poster_path.isNullOrEmpty()) {
                binding.ivPortada.setBackgroundResource(R.drawable.ic_noimage_foreground)
            }

            posterpath = Constants.PHOTO_BASE_URL + it.data!!.poster_path
            Glide.with(this).load(posterpath).into(binding.ivPortada)
            binding.tvTitle.text = it.data.original_title
            binding.idiomaOriginal.text = it.data.original_language
            binding.tvPopularity.text = it.data.popularity.toString()
            binding.tvOverview.movementMethod = ScrollingMovementMethod()
            binding.tvOverview.text = it.data.overview
            binding.tvReleaseDate.text = it.data.release_date
            val arrayGen = it.data.genres

            for (i in arrayGen) {
                Log.e("genero", i.name)
                genero = genero + " " + i.name
            }
            binding.tvGenero.text = genero


            title1 = it.data.original_title
            language1 = it.data.original_language
            popularidad = it.data.popularity.toString()
            overview = it.data.overview
            date = it.data.release_date

            //Guardamos en base local cuando es visitada por primera vez
            movieViewModel.insertMovieInDatabase(
                MovieEntitie(
                    data,
                    title1,
                    posterpath,
                    genero,
                    language1,
                    overview,
                    popularidad,
                    date
                )
            )

        })

        movieViewModel.progressbar.observe(this, Observer {
            binding.barProgress.isVisible = it
        })

        movieViewModel.responseFromDatabase.observe(this, Observer {

            if (it[0].poster_path.isNullOrEmpty()) {
                binding.ivPortada.setBackgroundResource(R.drawable.ic_noimage_foreground)
            }else{
                Glide.with(this).load(it[0].poster_path).into(binding.ivPortada)
            }
            binding.tvTitle.text = it[0].original_title
            binding.idiomaOriginal.text = it[0].original_language
            binding.tvPopularity.text = it[0].popularity.toString()
            binding.tvOverview.movementMethod = ScrollingMovementMethod()
            binding.tvOverview.text = it[0].overview
            binding.tvGenero.text = it[0].genero
            binding.tvReleaseDate.text = it[0].release_date
           // posterpath = it[0].poster_path
           // Glide.with(this).load(it[0].poster_path).into(binding.ivPortada)
            //Toast.makeText(this,"hola $posterpath",Toast.LENGTH_LONG).show()
        })


    }

    private fun setDataFromDatabase(data: Int) {
        movieViewModel.getMovieByIdFromDatabase(data)



    }


}