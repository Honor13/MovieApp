package com.example.movieapp.data.models.tvcredits


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class TvCredits(
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