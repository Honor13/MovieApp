package com.example.movieapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "favorites")
data class Favorites(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "favMovieId") val favMovieId: Int,
    @ColumnInfo(name = "movieId") @NotNull var movieId: Int,
    @ColumnInfo(name = "movieName") @NotNull val movieName: String,
    @ColumnInfo(name = "moviePosterPath") @NotNull val moviePosterPath: String,
    @ColumnInfo(name = "movieOverview") @NotNull val movieOverview: String,
    @ColumnInfo(name = "movieRating") @NotNull val rating: Double
)