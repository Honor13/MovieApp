package com.example.movieapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movieapp.data.database.entities.Favorites
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorites WHERE userId =:userId")
    fun getFavoritesByUserId(userId: String): Flow<List<Favorites>>

    @Insert
    suspend fun addFavorite(favMovie:Favorites)

    @Query("DELETE FROM favorites WHERE userId = :userId AND movieId = :movieId")
    suspend fun deleteFav(userId: String,movieId:Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE userId = :userId AND movieId = :movieId)")
     suspend fun existsFavorite(userId: String, movieId: Int): Boolean


}