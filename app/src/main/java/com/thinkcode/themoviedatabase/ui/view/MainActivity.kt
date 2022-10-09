package com.thinkcode.themoviedatabase.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.thinkcode.themoviedatabase.databinding.ActivityMainBinding
import com.thinkcode.themoviedatabase.ui.view.adapter.MovieAdapter
import com.thinkcode.themoviedatabase.ui.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val movieViewModel: MovieViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecylerview()


        //OBSERVERS FROM VIEWMODEL LIVEDATA
        movieViewModel.mutableList.observe(this, Observer {
            if (it != null) {
                binding.rvMovies.adapter = MovieAdapter(it.data!!.results)
            } else {
                binding.rvMovies.adapter = MovieAdapter(emptyList())
            }
        })
        movieViewModel.nolista.observe(this, Observer {
            Toast.makeText(this, "Check your internet Connection", Toast.LENGTH_LONG).show()
        })
        movieViewModel.progressbar.observe(this, Observer {
            binding.barProgress.isVisible = it
        })


    }

    private fun initRecylerview() {

        binding.rvMovies.layoutManager = GridLayoutManager(this, 2)


    }
}