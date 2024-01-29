package com.example.movieapp.data.network

import com.example.movieapp.data.models.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MoviesApi {
    @GET("movie/upcoming?language=en-US")
    suspend fun getUpComingMovies(
        @QueryMap queries: Map<String, String>
    ): Response<Movies>

}