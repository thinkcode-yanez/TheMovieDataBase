package com.thinkcode.themoviedatabase.ui.view

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.thinkcode.themoviedatabase.R
import com.thinkcode.themoviedatabase.core.Constants
import com.thinkcode.themoviedatabase.core.MovieApp.Companion.prefs
import com.thinkcode.themoviedatabase.data.MovieRepository
import com.thinkcode.themoviedatabase.data.database.entities.MovieEntitie
import com.thinkcode.themoviedatabase.databinding.ActivityDetailsBinding
import com.thinkcode.themoviedatabase.ui.viewmodel.MovieViewModel

class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding
    private val movieViewModel: MovieViewModel by viewModels()
    private lateinit var builder: AlertDialog.Builder


    var title1 = ""
    var language1 = ""
    var popularidad = ""
    var overview = ""
    var date = ""
    var genero = ""
    var posterpath = ""
    val openURL = Intent(Intent.ACTION_VIEW)


    var token = ""
    var session = ""
    var data = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        data = intent.getIntExtra("ID", 0)

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
            } else {
                Glide.with(this).load(it[0].poster_path).into(binding.ivPortada)
            }
            binding.tvTitle.text = it[0].original_title
            binding.idiomaOriginal.text = it[0].original_language
            binding.tvPopularity.text = it[0].popularity.toString()
            binding.tvOverview.movementMethod = ScrollingMovementMethod()
            binding.tvOverview.text = it[0].overview
            binding.tvGenero.text = it[0].genero
            binding.tvReleaseDate.text = it[0].release_date
        })


        //TOKENS,SESSIONSID,RATE
        movieViewModel.tokenResponse.observe(this, Observer {
            token = it.request_token
            // rateViewModel.setTokenOnDB(token)//GUARDAR TOKEN EN SHARED PREFERENCES
            openURL.data = Uri.parse("https://www.themoviedb.org/authenticate/$token/allow")
            startActivity(openURL)
        })
        movieViewModel.sessionResponse.observe(this, Observer {
            session = it.session_id
            prefs.saveSessionId(session)//SALVO LA SESION EN SHARED PREFERENCE
            Log.e("sesion", it.toString())
           // Toast.makeText(this, "Session Valida por 24 horas", Toast.LENGTH_LONG).show()

            if(it.success==false){
                movieViewModel.getToken()
            }

        })
        movieViewModel.rateResponse.observe(this, Observer {
               // Toast.makeText(this,"Get new session id",Toast.LENGTH_LONG).show()
               Toast.makeText(this, it!!.status_message.toString(), Toast.LENGTH_LONG).show()
               // Log.e("rateActivity", it.status_message)
               // Log.e("rateCode", it.status_code.toString())*/

        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sessionMenu -> {
                //rateViewModel.getDataSavedFromDB()
                if(prefs.getSessionId().isNullOrEmpty()){
                    movieViewModel.getToken()//PIDO TOKEN
                }else{
                    Toast.makeText(this,"You have valid session active!",Toast.LENGTH_SHORT).show()
                }
            }
            R.id.rateMenu -> {
                if (prefs.getSessionId().isEmpty()) {
                    Toast.makeText(this, "You should log in first!", Toast.LENGTH_SHORT).show()
                } else {

                    showDialog()

                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showDialog() {
        builder = AlertDialog.Builder(this)
        // Set up the input
        val input = EditText(this)
        input.setHint("Enter Number")
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setIcon(R.drawable.ic_launcher_foreground)
        builder.setView(input)
        builder.setTitle("Rate")
        builder.setMessage("Type 1 to 10 to rate!!")

    // Set up the buttons
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
            val m_Text = input.text.toString().toInt()
            if(m_Text in 1..10) {
                val rate= "$m_Text.0"
                movieViewModel.setRate(data, prefs.getSessionId(), rate.toDouble() )
                //movieViewModel.setRate(data, "897ed660e71a477d29a7d31c5bb360c076e95cac", rate.toDouble() )
            }else{
                Toast.makeText(this,"Choose range from 1 to 10",Toast.LENGTH_LONG).show()
            }
        })
        builder.setNegativeButton(
            "Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show()
    }


    override fun onResume() {
        super.onResume()
        if (!token.isEmpty()) {
            movieViewModel.getSession(token)
            Toast.makeText(this, "Token Saved", Toast.LENGTH_LONG).show()
        } else {
           // Toast.makeText(this, "", Toast.LENGTH_LONG).show()
        }

    }



}