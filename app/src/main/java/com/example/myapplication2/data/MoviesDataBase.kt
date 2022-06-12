package com.example.myapplication2.data

data class MoviesDataBase(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)