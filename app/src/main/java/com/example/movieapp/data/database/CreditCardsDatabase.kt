package com.example.movieapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.data.database.dao.CreditCardsDao
import com.example.movieapp.data.database.entities.CreditCards

@Database(entities = [CreditCards::class], version = 1)
abstract class CreditCardsDatabase : RoomDatabase() {

    abstract fun getCreditCards(): CreditCardsDao
}