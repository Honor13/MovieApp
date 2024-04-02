package com.example.movieapp.data.repository

import com.example.movieapp.data.database.datasource.FavoritesDataSource


class FavoritesRepository(var fDso: FavoritesDataSource) {

    fun getFavoritesByUserId(userId: String) = fDso.getFavoritesByUserId(userId)

    suspend fun addFavorites(userId: String,movieId: Int,movieName: String, moviePosterPath:String,moviewOverview: String, rating:Double)
    = fDso.addFavorites(userId,movieId,movieName,moviePosterPath,moviewOverview,rating)

    suspend fun deleteFavorites(userId: String,movieId: Int) = fDso.deleteFavorites(userId,movieId)

}