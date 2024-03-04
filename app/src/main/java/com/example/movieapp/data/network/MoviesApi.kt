package com.example.movieapp.data.network

import com.example.movieapp.data.models.Movies
import com.example.movieapp.data.models.TVs
import com.example.movieapp.data.models.moviedetails.Credits
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("movie/top_rated?language=en-US")
    suspend fun getTopRatedMovies(
        @QueryMap queries: Map<String, String>
    ):Response<Movies>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @QueryMap queries: Map<String, String>
    ): Response<Credits>

}