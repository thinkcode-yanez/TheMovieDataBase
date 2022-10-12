package com.thinkcode.themoviedatabase.ui.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AbsListView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.liveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.thinkcode.themoviedatabase.data.MovieRepository
import com.thinkcode.themoviedatabase.data.model.TMDBModel
import com.thinkcode.themoviedatabase.databinding.ActivityMainBinding
import com.thinkcode.themoviedatabase.ui.view.adapter.MovieAdapter
import com.thinkcode.themoviedatabase.ui.view.adapter.PageAdapter
import com.thinkcode.themoviedatabase.ui.viewmodel.MovieViewModel
import com.thinkcode.themoviedatabase.ui.viewmodel.PopularViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Test
    private lateinit var mAdapter:PageAdapter
    private val popularViewModel:PopularViewModel by viewModels()




    private val movieViewModel: MovieViewModel by viewModels()
    private val movieRepository = MovieRepository()
    private var currentPage = 1
    private var totalAvailablePages = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //initRecylerview()

        initRecyclerPager()
        loadingData()

       // val layoutManager: LinearLayoutManager = LinearLayoutManager(this)



      /*  binding.rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(r: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(r, dx, dy)

                if(dy==r.getChildAt(0).measuredHeight-r.measuredHeight){
                    currentPage+=1
                    movieViewModel.getAllPopularMovies("en", currentPage)
                    Toast.makeText(this@MainActivity, "hola el final", Toast.LENGTH_LONG).show()

                }
            }

        })

        //OBSERVERS FROM VIEWMODEL LIVEDATA
        movieViewModel.pupularList.observe(this, Observer {
            totalAvailablePages = it.data!!.total_pages
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
        })*/


    }

    private fun loadingData() {
        lifecycleScope.launch {
            popularViewModel.listData.collect(){
                mAdapter.submitData(it)
            }
        }
    }

    private fun initRecylerview() {

        binding.rvMovies.layoutManager = GridLayoutManager(this, 2)


    }
    private fun initRecyclerPager(){
        mAdapter=PageAdapter()

        binding.rvMovies.apply {
            layoutManager = StaggeredGridLayoutManager(
                2,StaggeredGridLayoutManager.VERTICAL
            )
            adapter=mAdapter
            setHasFixedSize(true)
        }

    }

}