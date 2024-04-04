package com.example.movieapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "creditCards")
data class CreditCards(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "creditCardId") val creditCardId: Int,
    @ColumnInfo(name="userId") @NotNull val userId:String,
    @ColumnInfo(name = "cardHolderName") @NotNull val cardholderName: String,
    @ColumnInfo(name = "bankName") @NotNull val bankName: String,
    @ColumnInfo(name = "cardNumber") @NotNull val cardNumber: String,
    @ColumnInfo(name = "expireDate") @NotNull val expireDate: String
)
