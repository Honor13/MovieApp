package com.example.movieapp.data.models.actordetails


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class ActorDetails(
    @SerializedName("adult")
    @Expose
    val adult: Boolean,
    @SerializedName("also_known_as")
    @Expose
    val alsoKnownAs: List<String>,
    @SerializedName("biography")
    @Expose
    val biography: String,
    @SerializedName("birthday")
    @Expose
    val birthday: String,
    @SerializedName("deathday")
    @Expose
    val deathday: Any,
    @SerializedName("gender")
    @Expose
    val gender: Int,
    @SerializedName("homepage")
    @Expose
    val homepage: Any,
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("imdb_id")
    @Expose
    val imdbId: String,
    @SerializedName("known_for_department")
    @Expose
    val knownForDepartment: String,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("place_of_birth")
    @Expose
    val placeOfBirth: String,
    @SerializedName("popularity")
    @Expose
    val popularity: Double,
    @SerializedName("profile_path")
    @Expose
    val profilePath: String
)