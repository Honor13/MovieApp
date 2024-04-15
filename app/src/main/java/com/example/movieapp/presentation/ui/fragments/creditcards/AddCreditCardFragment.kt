package com.example.movieapp.presentation.ui.fragments.creditcards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentAddCreditCardBinding
import com.example.movieapp.presentation.ui.viewmodels.CreditCardsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCreditCardFragment : Fragment() {
    private lateinit var binding: FragmentAddCreditCardBinding
    private lateinit var creditCardsViewModel: CreditCardsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_add_credit_card, container, false)
        binding.objectAddCreditCardFragment = this

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        creditCardsViewModel = ViewModelProvider(this).get(CreditCardsViewModel::class.java)
    }

    fun addCreditCard(cardHolderName: String, bankName: String, cardNumber: String, expireDate: String){
        creditCardsViewModel.addCreditCard("userId", cardHolderName,bankName,cardNumber, expireDate)
    }


}