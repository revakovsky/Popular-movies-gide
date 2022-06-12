package com.example.myapplication2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication2.data.MovieDetails
import com.example.myapplication2.data.MoviesDataBase
import com.example.myapplication2.data.Result
import com.example.myapplication2.model.repository.MoviesDBRepository1
import com.example.myapplication2.model.repository.MoviesDBRepository1Impl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesViewModel {

    private val _movies = MutableLiveData<List<Result?>>()
    val movies: LiveData<List<Result?>> = _movies

    private val _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails> = _movieDetails

    private val mMoviesRepository: MoviesDBRepository1 = MoviesDBRepository1Impl()

    fun getMovies() {
        val response = mMoviesRepository.getMovies()
        response.enqueue(object : Callback<MoviesDataBase> {
            override fun onResponse(
                call: Call<MoviesDataBase>?,
                response: Response<MoviesDataBase>?
            ) {
                Log.d("testLogs", "onResponse: success ${call.toString()}")
                _movies.postValue(response?.body()?.results)
            }

            override fun onFailure(call: Call<MoviesDataBase>, t: Throwable) {
                Log.d("testLogs", "onFailure: fail ${t.message}")
            }
        })
    }

    fun getMovieDetails(id: Int) {
        val response = mMoviesRepository.getMovieDetails(id)
        response.enqueue(object : Callback<MovieDetails> {
            override fun onResponse(
                call: Call<MovieDetails>?,
                response: Response<MovieDetails>?
            ) {
                Log.d("testLogs", "onResponse: success ${call.toString()}")
                _movieDetails.postValue(response?.body())
            }

            override fun onFailure(call: Call<MovieDetails>?, t: Throwable?) {
                Log.d("testLogs" , "onFailure: fail ${t?.message}")
            }
        })
    }
}
