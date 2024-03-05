package com.example.movieapp.data.network

import com.example.movieapp.data.models.Movies
import com.example.movieapp.data.models.TVs
import com.example.movieapp.data.models.moviecredits.Credits
import com.example.movieapp.data.models.moviedetails.DetailsResult
import com.example.movieapp.data.models.tvDetails.DetailsTVResult
import com.example.movieapp.data.models.tvcredits.TvCredits
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

    @GET("movie/{movie_id}?language=en-US")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @QueryMap queries: Map<String, String>
    ):Response<DetailsResult>

    @GET("tv/{series_id}?language=en-US")
    suspend fun getTvSeriesDetails(
        @Path("series_id") seriesId: Int,
        @QueryMap queries: Map<String, String>
    ):Response<DetailsTVResult>
    //https://api.themoviedb.org/3/tv/206829/credits?language=en-US&api_key=7c646d0a3ce431c66c2408250dbc2bfe
    @GET("tv/{series_id}/credits")
    suspend fun getTvCredits(
        @Path("series_id") seriesId: Int,
        @QueryMap queries: Map<String, String>
    ): Response<TvCredits>
}