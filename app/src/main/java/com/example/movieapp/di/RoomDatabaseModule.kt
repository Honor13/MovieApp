package com.example.movieapp.di

import android.content.Context
import androidx.room.Room
import com.example.movieapp.data.database.CreditCardsDatabase
import com.example.movieapp.data.database.FavoritesDatabase
import com.example.movieapp.data.database.dao.CreditCardsDao
import com.example.movieapp.data.database.dao.FavoritesDao
import com.example.movieapp.data.database.datasource.CreditCardsDataSource
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
    // Favorites Database operations Dependencies
    @Provides
    @Singleton
    fun provideFavoritesRepository(fDso: FavoritesDataSource): FavoritesRepository =
        FavoritesRepository(fDso)

    @Provides
    @Singleton
    fun provideFavoritesDataSource(favDao: FavoritesDao): FavoritesDataSource =
        FavoritesDataSource(favDao)

    @Provides
    @Singleton
    fun provideFavoritesDao(@ApplicationContext context: Context): FavoritesDao {
        val db =
            Room.databaseBuilder(context, FavoritesDatabase::class.java, "FavoriteMovies.sqlite")
                .build()
        return db.getFavorites()
    }

    // Credit Card Database operations Dependencies


    @Provides
    @Singleton
    fun provideCreditCardsDataSource(creditsDao: CreditCardsDao) = CreditCardsDataSource(creditsDao)

    @Provides
    @Singleton
    fun provideCreditCardsDao(@ApplicationContext context: Context): CreditCardsDao {
        val db =
            Room.databaseBuilder(context, CreditCardsDatabase::class.java, "CreditCards.sqlite")
                .build()
        return db.getCreditCards()
    }
}