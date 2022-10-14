package com.thinkcode.themoviedatabase.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnAttachStateChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView.OnCloseListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.thinkcode.themoviedatabase.data.MovieRepository
import com.thinkcode.themoviedatabase.data.database.entities.MovieEntitie
import com.thinkcode.themoviedatabase.data.paging.MoviePagingSource
import com.thinkcode.themoviedatabase.databinding.ActivityMainBinding
import com.thinkcode.themoviedatabase.ui.view.adapter.MovieAdapter
import com.thinkcode.themoviedatabase.ui.view.adapter.PageAdapter
import com.thinkcode.themoviedatabase.ui.viewmodel.MovieViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding

    //Test
    private val movieRepository = MovieRepository()
    private lateinit var mAdapter: PageAdapter

   val moviePagingSource=MoviePagingSource()

    private val movieViewModel: MovieViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.svMovie.setOnQueryTextListener(this)
        initRecyclerPager()
        // loadingData()
       /* var lista = emptyList<MovieEntitie>()

            GlobalScope.launch {
                lista = movieRepository.getAllMovieFromDataBase()
                Log.e("lista", lista[0].original_title)
                Log.e("lista", lista[1].original_title)
                Log.e("lista", lista[2].original_title)
                Log.e("lista", lista[3].original_title)
                Log.e("lista", lista[4].original_title)
                Log.e("lista", lista[5].original_title)





        }*/


        //OBSERVERS FROM VIEWMODEL LIVEDATA
        movieViewModel.pupularList.observe(this, Observer {

            if (it != null) {
                binding.rvMovies.adapter = MovieAdapter(it.data!!.results)
            } else {
                binding.rvMovies.adapter = MovieAdapter(emptyList())
            }
        })

        movieViewModel.nolista.observe(this, Observer {
            if (it == true) {
                Toast.makeText(this, "Check your internet Connection", Toast.LENGTH_LONG).show()
            }
        })

        movieViewModel.progressbar.observe(this, Observer {
            binding.barProgress.isVisible = it
            if (it == false) {
                // hidekeyboard()
            }
        })


    }

    private fun loadingData() {

        lifecycleScope.launch {
            movieViewModel.listData.collect() {
                mAdapter.submitData(it)

            }
        }

    }

    private fun initRecyclerPager() {
       // binding.barProgress.isVisible=true
        mAdapter = PageAdapter()
        binding.rvMovies.apply {
            layoutManager = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
            )
            adapter = mAdapter
            setHasFixedSize(true)
        }
        loadingData()


    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        /* if (!query.isNullOrEmpty()) {

             movieViewModel.searhMovie(query.lowercase())
         }*/
        hidekeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (!newText.isNullOrEmpty()) {

            movieViewModel.searhMovie(newText.lowercase())
        } else {
            hidekeyboard()
            initRecyclerPager()
        }
        return true
    }


    private fun hidekeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    override fun onResume() {
        super.onResume()
        hidekeyboard()
    }

}