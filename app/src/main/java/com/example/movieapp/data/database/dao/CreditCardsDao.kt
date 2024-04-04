package com.example.movieapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movieapp.data.database.entities.CreditCards
import kotlinx.coroutines.flow.Flow


@Dao
interface CreditCardsDao {

    @Query("SELECT * FROM creditCards WHERE userId =:userId")
    fun getCreditCardsByUserId(userId: String): Flow<List<CreditCards>>

    @Insert
    suspend fun addCreditCards(creditCards: CreditCards)

    @Query("DELETE FROM creditCards WHERE userId =:userId AND creditCardId =:creditCardId")
    suspend fun deleteCreditCards(userId: String, creditCardId:Int)
}