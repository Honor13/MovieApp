package com.example.movieapp.data.models.tvDetails


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class CreatedBy(
    @SerializedName("credit_id")
    @Expose
    val creditId: String,
    @SerializedName("gender")
    @Expose
    val gender: Int,
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("profile_path")
    @Expose
    val profilePath: String
)