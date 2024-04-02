package com.example.movieapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.data.database.dao.FavoritesDao
import com.example.movieapp.data.database.entities.Favorites

@Database(entities = [Favorites::class], version = 1)
abstract class FavoritesDatabase : RoomDatabase() {

    abstract fun getFavorites():FavoritesDao
}