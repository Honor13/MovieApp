package com.example.movieapp.data.models.tvDetails


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class DetailsTVResult(
    @SerializedName("adult")
    @Expose
    val adult: Boolean,
    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String,
    @SerializedName("created_by")
    @Expose
    val createdBy: List<CreatedBy>,
    @SerializedName("episode_run_time")
    @Expose
    val episodeRunTime: List<Int>,
    @SerializedName("first_air_date")
    @Expose
    val firstAirDate: String,
    @SerializedName("genres")
    @Expose
    val genres: List<Genre>,
    @SerializedName("homepage")
    @Expose
    val homepage: String,
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("in_production")
    @Expose
    val inProduction: Boolean,
    @SerializedName("languages")
    @Expose
    val languages: List<String>,
    @SerializedName("last_air_date")
    @Expose
    val lastAirDate: String,
    @SerializedName("last_episode_to_air")
    @Expose
    val lastEpisodeToAir: LastEpisodeToAir,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("networks")
    @Expose
    val networks: List<Network>,
    @SerializedName("next_episode_to_air")
    @Expose
    val nextEpisodeToAir: NextEpisodeToAir,
    @SerializedName("number_of_episodes")
    @Expose
    val numberOfEpisodes: Int,
    @SerializedName("number_of_seasons")
    @Expose
    val numberOfSeasons: Int,
    @SerializedName("origin_country")
    @Expose
    val originCountry: List<String>,
    @SerializedName("original_language")
    @Expose
    val originalLanguage: String,
    @SerializedName("original_name")
    @Expose
    val originalName: String,
    @SerializedName("overview")
    @Expose
    val overview: String,
    @SerializedName("popularity")
    @Expose
    val popularity: Double,
    @SerializedName("poster_path")
    @Expose
    val posterPath: String,
    @SerializedName("production_companies")
    @Expose
    val productionCompanies: List<ProductionCompany>,
    @SerializedName("production_countries")
    @Expose
    val productionCountries: List<ProductionCountry>,
    @SerializedName("seasons")
    @Expose
    val seasons: List<Season>,
    @SerializedName("spoken_languages")
    @Expose
    val spokenLanguages: List<SpokenLanguage>,
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("tagline")
    @Expose
    val tagline: String,
    @SerializedName("type")
    @Expose
    val type: String,
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double,
    @SerializedName("vote_count")
    @Expose
    val voteCount: Int
)