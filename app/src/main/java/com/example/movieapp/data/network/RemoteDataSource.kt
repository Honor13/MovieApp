package com.example.movieapp.data.network

import com.example.movieapp.data.models.Movies
import com.example.movieapp.data.models.TVs
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val moviesApi: MoviesApi
) {

    suspend fun getUpComingMovies(queries: Map<String, String>): Response<Movies> {
        return moviesApi.getUpComingMovies(queries)
    }

    suspend fun getTrendingMovies(queries: Map<String, String>): Response<Movies> {
        return moviesApi.getTrendingMovies(queries)
    }

    suspend fun getTrendingTVs(queries: Map<String, String>): Response<TVs> {
        return moviesApi.getTrendingTVs(queries)
    }

    suspend fun getPopularMovies(queries: Map<String, String>): Response<Movies> {
        return moviesApi.getPopularMovies(queries)
    }

}