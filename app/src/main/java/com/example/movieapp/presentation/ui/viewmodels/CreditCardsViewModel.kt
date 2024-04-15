package com.example.movieapp.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.database.entities.CreditCards
import com.example.movieapp.data.repository.CreditCardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreditCardsViewModel @Inject constructor(
    var creditCardsRepository: CreditCardsRepository
) :
    ViewModel() {

    private val _listCard = MutableStateFlow<List<CreditCards>>(emptyList())
    val listCard: StateFlow<List<CreditCards>> = _listCard.asStateFlow()

     var isEmpty = MutableStateFlow<Boolean>(false)

    init {
        viewModelScope.launch {
            listCard.collect { cards ->
                isEmpty.value = cards.isEmpty()
            }
        }
    }


    // Verileri güncelleyen işlev
    fun updateCardList() {
        val isListCardEmpty = listCard.value.isEmpty()

        isEmpty.value = isListCardEmpty


    }
    fun addCreditCard(
        userId: String,
        cardHolderName: String,
        bankName: String,
        cardNumber: String,
        expireDate: String
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            creditCardsRepository.addCreditCards(
                userId,
                cardHolderName,
                bankName,
                cardNumber,
                expireDate
            )
        }

    }

    fun getCreditCards(userId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            creditCardsRepository.getCreditCardsByUserId(userId).collect { cards ->
                _listCard.value = cards
            }

        }
    }

    fun deleteCreditCards(userId: String, creditCardId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            creditCardsRepository.deleteCreditCard(userId,creditCardId)
        }
    }




}