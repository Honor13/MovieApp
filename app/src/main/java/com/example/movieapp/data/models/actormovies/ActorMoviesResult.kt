package com.example.movieapp.data.models.actormovies


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class ActorMoviesResult(
    @SerializedName("cast")
    @Expose
    val cast: List<Cast>,
    @SerializedName("crew")
    @Expose
    val crew: List<Crew>,
    @SerializedName("id")
    @Expose
    val id: Int
)