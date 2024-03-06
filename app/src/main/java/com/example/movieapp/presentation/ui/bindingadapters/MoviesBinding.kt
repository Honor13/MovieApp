package com.example.movieapp.presentation.ui.bindingadapters
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.movieapp.R
import com.example.movieapp.presentation.ui.fragments.bottomnavholder.BottomAppBarHolderFragmentDirections
import com.example.movieapp.presentation.ui.fragments.details.MovieDetailsFragmentDirections
import com.example.movieapp.util.Constants.Companion.IMAGES_BASE_URL
import com.example.movieapp.util.Constants.Companion.IMAGE_200
import com.example.movieapp.util.Constants.Companion.IMAGE_400
import com.example.movieapp.util.Constants.Companion.IMAGE_500


class MoviesBinding {

    companion object {

        @BindingAdapter("onsetOnClickListener")
        @JvmStatic
        fun onActorDetailsonClickListener(personLayout: ConstraintLayout, personId: Int){
            personLayout.setOnClickListener {
                val action = MovieDetailsFragmentDirections.actionDetailsFragmentToActorDetailsFragment(personId)
                personLayout.findNavController().navigate(action)
            }
        }

        @BindingAdapter("tVseries_id","tVposterPath", requireAll = false)
        @JvmStatic
        fun onTVSeriesSetOnClickListener(moviesRowLayout: ConstraintLayout,seriesId: Int,posterPath: String){
            moviesRowLayout.setOnClickListener {
                val action = BottomAppBarHolderFragmentDirections.actionBottomAppHolderFragmentToTvSeriesDetailsFragment(
                    posterPath,
                    seriesId
                )
                moviesRowLayout.findNavController().navigate(action)
            }
        }

        @BindingAdapter("movie_id","posterPath", requireAll = false)
        @JvmStatic
        fun onMoviesSetOnClickListener(moviesRowLayout: ConstraintLayout,movieId: Int,posterPath: String){

            moviesRowLayout.setOnClickListener {
                val action = BottomAppBarHolderFragmentDirections.actionBottomAppHolderFragmentToDetailsFragment(
                    movieId,
                    posterPath
                )
                moviesRowLayout.findNavController().navigate(action)
            }
        }

        @BindingAdapter("vPmovie_id","vPposterPath", requireAll = false)
        @JvmStatic
        fun onVPMoviesSetOnClickListener(moviesRowLayout: LinearLayout,movieId: Int,posterPath: String){
            moviesRowLayout.setOnClickListener {
                val action = BottomAppBarHolderFragmentDirections.actionBottomAppHolderFragmentToDetailsFragment(
                    movieId,
                    posterPath
                )
                moviesRowLayout.findNavController().navigate(action)
            }
        }

        @BindingAdapter("loadImageFromUrl200")
        @JvmStatic
        fun loadingImageFromUrl200(imageView: ImageView, imageUrl : String){

            imageView.load(IMAGES_BASE_URL + IMAGE_200 + imageUrl){
                crossfade(600)
                error(R.drawable.ic_error_placeholder)

            }
        }

        @BindingAdapter("loadImageFromUrl400")
        @JvmStatic
        fun loadingImageFromUrl400(imageView: ImageView, imageUrl : String?){

            if (imageUrl != null) {
                imageView.load(IMAGES_BASE_URL + IMAGE_400 + imageUrl){
                    crossfade(600)
                    error(R.drawable.nophoto)

                }
            }else {
                imageView.load(R.drawable.nophoto)
            }

        }

        @BindingAdapter("loadImageFromUrl500")
        @JvmStatic
        fun loadingImageFromUrl500(imageView: ImageView, imageUrl : String){

            imageView.load(IMAGES_BASE_URL + IMAGE_500 + imageUrl){
                crossfade(600)
                error(R.drawable.ic_error_placeholder)

            }
        }
    }

}