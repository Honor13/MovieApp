package com.example.movieapp.data.database.datasource

import com.example.movieapp.data.database.dao.CreditCardsDao
import com.example.movieapp.data.database.entities.CreditCards
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreditCardsDataSource @Inject constructor(private val creditDao: CreditCardsDao) {

    fun getCreditCardsByUserId(userId: String): Flow<List<CreditCards>> =
        creditDao.getCreditCardsByUserId(userId)

    suspend fun addCreditCards(
        userId: String,
        cardHolderName: String,
        bankname: String,
        cardNumber: String,
        expireDate: String
    ) {
        val newCard = CreditCards(0, userId, cardHolderName, bankname, cardNumber, expireDate)
        creditDao.addCreditCards(newCard)
    }

    suspend fun deleteCreditCards(userId: String, creditCardId: Int) =
        creditDao.deleteCreditCards(userId, creditCardId)
}