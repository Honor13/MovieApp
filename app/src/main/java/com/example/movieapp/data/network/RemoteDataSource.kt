package com.example.movieapp.data.network

import com.example.movieapp.data.models.Movies
import com.example.movieapp.data.models.TVs
import com.example.movieapp.data.models.moviecredits.Credits
import com.example.movieapp.data.models.moviedetails.DetailsResult
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

    suspend fun getTopRatedMovies(queries: Map<String, String>): Response<Movies> {
        return  moviesApi.getTopRatedMovies(queries)
    }

    suspend fun getMovieCredits(movieId:Int,queries: Map<String,String>): Response<Credits> {
        return moviesApi.getMovieCredits(movieId,queries)
    }

    suspend fun getMovieDetails(movieId: Int, queries: Map<String, String>): Response<DetailsResult> {
        return moviesApi.getMovieDetails(movieId,queries)
    }



}