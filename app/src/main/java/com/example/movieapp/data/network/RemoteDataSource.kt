package com.example.movieapp.data.network

import com.example.movieapp.data.models.Movies
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val moviesApi: MoviesApi
) {

    suspend fun getUpComingMovies(queries: Map<String, String>):Response<Movies> {
        return moviesApi.getUpComingMovies(queries)
    }

    suspend fun getTrendingMovies(queries: Map<String, String>):Response<Movies> {
        return moviesApi.getTrendingMovies(queries)
    }

}