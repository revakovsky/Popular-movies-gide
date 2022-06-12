package com.example.myapplication2.model.repository

import com.example.myapplication2.Constants
import com.example.myapplication2.data.MovieDetails
import com.example.myapplication2.data.MoviesDataBase
import com.example.myapplication2.model.apis.ApiInterface
import retrofit2.Call

/**
 * Repository provides info taken from MovieDB API
**/

class MoviesDBRepository1Impl : MoviesDBRepository1 {

    //include retrofit in our code
    private val apiInterface = ApiInterface.create()

    override fun getMovies() : Call<MoviesDataBase> {
        return apiInterface.getMovies(Constants.API_KEY, "en-US", 1)
    }

    override fun getMovieDetails(id : Int) : Call<MovieDetails> {
        return apiInterface.getMovieDetails(id, Constants.API_KEY)
    }
}