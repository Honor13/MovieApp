package com.example.movieapp.presentation.ui.bindingadapters

import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import coil.load
import com.example.movieapp.R
import com.example.movieapp.util.Constants.Companion.IMAGES_BASE_URL
import com.example.movieapp.util.Constants.Companion.IMAGE_200

class MoviesBinding {

    companion object {
        @BindingAdapter("loadImageFromUrl200")
        @JvmStatic
        fun loadingImageFromUrl200(imageView: ImageView, imageUrl : String){

            imageView.load(IMAGES_BASE_URL + IMAGE_200 + imageUrl){
                crossfade(600)
                error(R.drawable.ic_error_placeholder)

            }
        }
    }

}