package com.example.myapplication2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.squareup.picasso.Picasso

class MoviesDetailsActivity : AppCompatActivity() {
    private lateinit var title: TextView
    private lateinit var releaseDate: TextView
    private lateinit var score: TextView
    private lateinit var description: TextView

    private lateinit var banner: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_details)

        val id = intent.getIntExtra("id", 0)

        title = findViewById(R.id.movies_details_title)
        releaseDate = findViewById(R.id.movies_details_body_container_data)
        score = findViewById(R.id.movies_details_body_container_score)
        description = findViewById(R.id.movies_details_body_container_description)
        banner = findViewById(R.id.movies_details_image_banner)

        val apiInterface = id.let { ApiInterface.create().getMovieDetails(it,"248691836d283188a26679570aae0ef6") }

        //apiInterface.enqueue( Callback<List<Movie>>())
        apiInterface.enqueue( object : Callback<MovieDetails> {
            override fun onResponse(call: Call<MovieDetails>?, response: Response<MovieDetails>?) {

                title.text = response?.body()?.original_title
                releaseDate.text = response?.body()?.release_date
                score.text = response?.body()?.vote_average.toString()
                description.text = response?.body()?.overview

                Picasso.get().load("https://image.tmdb.org/t/p/w500/" + response?.body()?.backdrop_path).into(banner);
            }

            override fun onFailure(call: Call<MovieDetails>?, t: Throwable?) {
                Log.d("testLogs" , "onFailure: fail ${t?.message}")
            }
        })

    }
}