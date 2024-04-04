package com.example.movieapp.data.repository

import com.example.movieapp.data.database.datasource.CreditCardsDataSource

class CreditCardsRepository(var cDso: CreditCardsDataSource) {

    fun getCreditCardsByUserId(userId: String) = cDso.getCreditCardsByUserId(userId)

    suspend fun addCreditCards(
        userId: String,
        cardHolderName: String,
        bankName: String,
        cardNumber: String,
        expireDate: String
    ) = cDso.addCreditCards(userId, cardHolderName, bankName, cardNumber, expireDate)

    suspend fun deleteCreditCard(userId: String, creditCardId: Int) =
        cDso.deleteCreditCards(userId, creditCardId)
}