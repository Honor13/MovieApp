package com.example.movieapp.data.models.moviedetails


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable

data class ProductionCompany(
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("logo_path")
    @Expose
    val logoPath: String?,
    @SerializedName("name")
    @Expose
    val name: String?,
    @SerializedName("origin_country")
    @Expose
    val originCountry: String?
):Serializable