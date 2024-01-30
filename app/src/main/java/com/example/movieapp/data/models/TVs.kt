package com.example.movieapp.data.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class TVs(
    @SerializedName("page")
    @Expose
    val page: Int,
    @SerializedName("results")
    @Expose
    val results: List<ResultTV>,
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int,
    @SerializedName("total_results")
    @Expose
    val totalResults: Int
)