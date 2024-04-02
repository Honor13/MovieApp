package com.example.movieapp.data.database.datasource

import com.example.movieapp.data.database.dao.FavoritesDao
import com.example.movieapp.data.database.entities.Favorites
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesDataSource @Inject constructor(private val favDao: FavoritesDao) {

    fun getFavoritesByUserId(userId: String): Flow<List<Favorites>> =
        favDao.getFavoritesByUserId(userId)

    suspend fun addFavorites(
        userId: String,
        movieId: Int,
        movieName: String,
        moviePosterPath: String,
        movieOverview: String,
        rating: Double
    ) {
        val newFav =
            Favorites(0, userId, movieId, movieName, moviePosterPath, movieOverview, rating)
        favDao.addFavorite(newFav)
    }


    suspend fun deleteFavorites(userId: String, favMovieId: Int) =
        favDao.deleteFav(userId, favMovieId)
}