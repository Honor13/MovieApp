package com.example.movieapp.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.models.Movies
import com.example.movieapp.data.models.Result
import com.example.movieapp.databinding.TrendingMoviesCardLayoutBinding
import com.example.movieapp.util.MoviesDiffUtil
import java.util.Locale

class TrendingMoviesAdapter: RecyclerView.Adapter<TrendingMoviesAdapter.MyViewHolder>() {

    private var trendingMovies = emptyList<Result>()
    class MyViewHolder(private val binding:TrendingMoviesCardLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.result = result
            binding.vote =  String.format(Locale.US,"%,.1f",result.voteAverage)

            binding.executePendingBindings()
        }

        companion object {

            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TrendingMoviesCardLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return trendingMovies.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTrendMovies = trendingMovies[position]
        holder.bind(currentTrendMovies)
    }

    fun setData(newData: Movies) {
        val trendingMoviesDiffUtil = MoviesDiffUtil(trendingMovies, newData.results)
        val trendingDiffUtilResult = DiffUtil.calculateDiff(trendingMoviesDiffUtil)
        trendingMovies = newData.results
        trendingDiffUtilResult.dispatchUpdatesTo(this)
    }
}