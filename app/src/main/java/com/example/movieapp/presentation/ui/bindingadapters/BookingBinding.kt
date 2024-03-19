package com.example.movieapp.presentation.ui.bindingadapters

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.movieapp.R
import com.example.movieapp.presentation.ui.viewmodels.BookingViewModel

class BookingBinding {

    companion object {
        private val selectedSeatIds = mutableListOf<String>()

        @BindingAdapter("applySeats")
        @JvmStatic
        fun applySeats(view: ImageView, state: Boolean) {
            if (state) {
                view.setImageResource(
                    R.drawable.seat_not_available

                )

            } else {
                view.setImageResource(
                    R.drawable.seat_available

                )
            }

        }

        @BindingAdapter("selectSeat","seatId","viewModel","isClickable", requireAll = false)
        @JvmStatic
        fun selectSeat(view: ImageView, state: Boolean, seatId: String,viewModel: BookingViewModel,isClickable: Boolean) {
            var isSelected = state
            view.setOnClickListener {
                isSelected = !isSelected

                // isSelected true ise yeni resmi, değilse eski resmi ayarla
                if (!isClickable){
                    if (isSelected) {
                        view.setImageResource(R.drawable.seat_selected)
                        selectedSeatIds.add(seatId)
                        viewModel.getIds()
                    } else {
                        view.setImageResource(R.drawable.seat_available)
                        selectedSeatIds.remove(seatId)
                        viewModel.getIds()
                    }
                }


            }
        }

        fun getSelectedSeatIds(): List<String> {
            return selectedSeatIds
        }

    }
}


