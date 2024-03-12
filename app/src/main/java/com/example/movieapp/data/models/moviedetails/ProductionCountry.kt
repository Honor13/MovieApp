package com.example.movieapp.data.models.moviedetails


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    @Expose
    val iso31661: String?,
    @SerializedName("name")
    @Expose
    val name: String?
):Serializable