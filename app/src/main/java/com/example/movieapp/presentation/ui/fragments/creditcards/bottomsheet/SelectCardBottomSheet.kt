package com.example.movieapp.presentation.ui.fragments.creditcards.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentSelectCardBottomSheetBinding
import com.example.movieapp.presentation.ui.adapters.CreditCardsAdapter
import com.example.movieapp.presentation.ui.viewmodels.CreditCardsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SelectCardBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSelectCardBottomSheetBinding
    private lateinit var creditCardsViewModel: CreditCardsViewModel
    private val cAdapter: CreditCardsAdapter by lazy {
        CreditCardsAdapter(onItemClickListener = { favorites, creditCardId ->
            creditCardsViewModel.deleteCreditCards("userId", creditCardId)
            creditCardsViewModel.updateCardList()
        })
    }
//    private val cAdapter: CreditCardsAdapter by lazy {
//        CreditCardsAdapter()
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_select_card_bottom_sheet, container, false)

        setupRecyclerView()
        creditCardsViewModel.updateCardList()

        lifecycleScope.launch {
            creditCardsViewModel.isEmpty.collect { isEmpty ->
                withContext(Dispatchers.Main) {
                    if (!isEmpty) {
                        Log.e("Dante", "fragment isEmpty value: ${isEmpty}")
                        binding.rVCards.visibility = View.VISIBLE
                        binding.imageView10.visibility = View.INVISIBLE
                        binding.textView42.visibility = View.INVISIBLE


                    } else {
                        Log.e("Dante",isEmpty.toString())
                        Log.e("Dante", "fragment isEmpty value: ${isEmpty}")
                        binding.rVCards.visibility = View.INVISIBLE
                        binding.imageView10.visibility = View.VISIBLE
                        binding.textView42.visibility = View.VISIBLE

                        creditCardsViewModel.getCreditCards("userId")

                    }
                }
            }
        }





        creditCardsViewModel.getCreditCards("userId")

        lifecycleScope.launch {
            creditCardsViewModel.listCard.collect { cards ->
                cAdapter.submitList(cards)
            }
        }
//        binding.buttonApprove.setOnClickListener {
//
//            firebaseViewModel.splitIdsAndSave(getIds,movie.id.toString(),date.toString(),time.toString())
//            getIds= emptyList()
//
//        }

        binding.textViewAddNewCard.setOnClickListener {
            findNavController().navigate(R.id.action_selectCardBottomSheet_to_addCreditCardFragment)
        }


        return binding.root
    }



    private fun setupRecyclerView() {
        binding.rVCards.adapter = cAdapter
        binding.rVCards.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        creditCardsViewModel = ViewModelProvider(this).get(CreditCardsViewModel::class.java)
    }
}