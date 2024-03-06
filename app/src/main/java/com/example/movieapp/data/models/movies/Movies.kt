package com.example.movieapp.data.models.movies


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Movies(
    @SerializedName("dates")
    @Expose
    val dates: Dates,
    @SerializedName("page")
    @Expose
    val page: Int,
    @SerializedName("results")
    @Expose
    val results: List<Result>,
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int,
    @SerializedName("total_results")
    @Expose
    val totalResults: Int
)