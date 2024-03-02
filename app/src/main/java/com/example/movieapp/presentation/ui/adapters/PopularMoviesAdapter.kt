package com.example.movieapp.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.models.Movies
import com.example.movieapp.data.models.Result
import com.example.movieapp.databinding.PopularMoviesCardLayoutBinding
import com.example.movieapp.util.MoviesDiffUtil
import java.util.Locale

class PopularMoviesAdapter:RecyclerView.Adapter<PopularMoviesAdapter.MyViewHolder>() {

    private var popularMovies = emptyList<Result>()
    class MyViewHolder(private var binding:PopularMoviesCardLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result){
            binding.result = result
            binding.vote =  String.format(Locale.US,"%,.1f",result.voteAverage)
            binding.executePendingBindings()
        }

        companion object {

            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PopularMoviesCardLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return  MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return popularMovies.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPopularMovies = popularMovies[position]
        holder.bind(currentPopularMovies)
    }

    fun setData(newData: Movies) {
        val popularMoviesDiffUtil = MoviesDiffUtil(popularMovies, newData.results)
        val popularDiffResult = DiffUtil.calculateDiff(popularMoviesDiffUtil)
        popularMovies = newData.results
        popularDiffResult.dispatchUpdatesTo(this)
    }
}