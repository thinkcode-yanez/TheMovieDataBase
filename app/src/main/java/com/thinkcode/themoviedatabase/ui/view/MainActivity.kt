package com.thinkcode.themoviedatabase.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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
import com.thinkcode.themoviedatabase.databinding.ActivityMainBinding
import com.thinkcode.themoviedatabase.ui.view.adapter.MovieAdapter
import com.thinkcode.themoviedatabase.ui.view.adapter.PageAdapter
import com.thinkcode.themoviedatabase.ui.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule



@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: PageAdapter
    private val movieViewModel: MovieViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.svMovie.setOnQueryTextListener(this)


        binding.barProgress.isVisible=true

        Handler().postDelayed({
            binding.barProgress.isVisible=false
        },3000)
        initRecyclerPager()






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
        movieViewModel.sinresultados.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "No Results.", Toast.LENGTH_LONG).show()
            }

        })


    }

    private fun loadingData(){
        lifecycleScope.launch {
            movieViewModel.listData.collect() {
                mAdapter.submitData(it)
            }
        }

    }

    private fun initRecyclerPager() {
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
        if (binding.rvMovies.adapter == null) {
            Toast.makeText(this, "No Data", Toast.LENGTH_LONG).show()
        }
    }

}