package com.example.movieapp.di

import android.content.Context
import androidx.room.Room
import com.example.movieapp.data.database.FavoritesDatabase
import com.example.movieapp.data.database.dao.FavoritesDao
import com.example.movieapp.data.database.datasource.FavoritesDataSource
import com.example.movieapp.data.repository.FavoritesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Provides
    @Singleton
    fun provideFavoritesRepository(fDso: FavoritesDataSource): FavoritesRepository = FavoritesRepository(fDso)

    @Provides
    @Singleton
    fun provideFavoritesDataSource(favDao: FavoritesDao): FavoritesDataSource = FavoritesDataSource(favDao)

    @Provides
    @Singleton
    fun provideFavoritesDao(@ApplicationContext context: Context) : FavoritesDao {
        val db = Room.databaseBuilder(context,FavoritesDatabase::class.java,"FavoriteMovies.sqlite")
            .build()
        return db.getFavorites()
    }
}