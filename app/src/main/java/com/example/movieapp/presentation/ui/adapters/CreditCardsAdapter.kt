package com.example.movieapp.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.database.entities.CreditCards
import com.example.movieapp.databinding.CreditCardCardDesignBinding
import com.example.movieapp.util.CreditCardsDiffCallback

class CreditCardsAdapter(private val onItemClickListener: (CreditCards, Int) -> Unit): RecyclerView.Adapter<CreditCardsAdapter.MyViewHolder>() {

    private var cardItems = emptyList<CreditCards>()
    class MyViewHolder(private val binding: CreditCardCardDesignBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(items: CreditCards,onItemClickListener: (CreditCards,Int) -> Unit){
            binding.objectCreditCard = items
            binding.imageViewDeleteCard.setOnClickListener {
                onItemClickListener.invoke(items,items.creditCardId)
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CreditCardCardDesignBinding.inflate(layoutInflater,parent,false)
                return  MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return cardItems.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentCardItems = cardItems[position]
        holder.bind(currentCardItems,onItemClickListener)
    }

    fun submitList(newList: List<CreditCards>) {
        val diffResult = DiffUtil.calculateDiff(
            CreditCardsDiffCallback(cardItems, newList)
        )
        cardItems = newList
        diffResult.dispatchUpdatesTo(this)
    }
}