package com.example.movieapp.data.database.entities

data class CreditCards(
    val creditCardId: Int,
    val cardholderName: String,
    val bankName: String,
    val cardNumber: String,
    val expireDate: String
)
