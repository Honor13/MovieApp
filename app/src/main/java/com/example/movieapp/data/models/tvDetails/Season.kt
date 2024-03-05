package com.example.movieapp.data.models.tvDetails


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Season(
    @SerializedName("air_date")
    @Expose
    val airDate: String,
    @SerializedName("episode_count")
    @Expose
    val episodeCount: Int,
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("overview")
    @Expose
    val overview: String,
    @SerializedName("poster_path")
    @Expose
    val posterPath: String,
    @SerializedName("season_number")
    @Expose
    val seasonNumber: Int,
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double
)