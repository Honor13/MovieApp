package com.example.movieapp.data.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Dates(
    @SerializedName("maximum")
    @Expose
    val maximum: String,
    @SerializedName("minimum")
    @Expose
    val minimum: String
)