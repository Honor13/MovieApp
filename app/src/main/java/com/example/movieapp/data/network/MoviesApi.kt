package com.example.movieapp.data.network

import com.example.movieapp.data.models.Movies
import com.example.movieapp.data.models.TVs
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MoviesApi {
    @GET("movie/upcoming?language=en-US")
    suspend fun getUpComingMovies(
        @QueryMap queries: Map<String, String>
    ): Response<Movies>


    @GET("trending/movie/day?language=en-US")
    suspend fun getTrendingMovies(
        @QueryMap queries: Map<String, String>
    ):Response<Movies>

    @GET("trending/tv/day?language=en-US")
    suspend fun getTrendingTVs(
        @QueryMap queries: Map<String, String>
    ):Response<TVs>

    @GET("movie/popular?language=en-US")
    suspend fun getPopularMovies(
        @QueryMap queries: Map<String, String>
    ):Response<Movies>


}