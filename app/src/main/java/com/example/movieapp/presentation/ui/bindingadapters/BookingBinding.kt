package com.example.movieapp.presentation.ui.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.movieapp.R

class BookingBinding {

    companion object {

        @BindingAdapter("applySeats")
        @JvmStatic
        fun applySeats(view: ImageView, state: Boolean){
            if (state){
                view.setImageResource(
                    R.drawable.seat_available
                )
            }else{
                view.setImageResource(
                    R.drawable.seat_not_available
                )
            }
        }
    }
}