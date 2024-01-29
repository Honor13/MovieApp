package com.example.movieapp.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.models.Movies
import com.example.movieapp.data.models.Result
import com.example.movieapp.databinding.ComingSoonCardLayoutBinding
import com.example.movieapp.util.MoviesDiffUtil

class UpComingMoviesAdapter: RecyclerView.Adapter<UpComingMoviesAdapter.MyViewHolder>() {

    private var upComingMovies = emptyList<Result>()

    class MyViewHolder(private val binding:ComingSoonCardLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ComingSoonCardLayoutBinding.inflate(layoutInflater,parent,false)
                return  MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return upComingMovies.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentMovies = upComingMovies[position]
        holder.bind(currentMovies)
    }

    fun setData(newData: Movies){
        val moviesDiffUtil = MoviesDiffUtil(upComingMovies,newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(moviesDiffUtil)
        upComingMovies = newData.results
        diffUtilResult.dispatchUpdatesTo(this)

    }
}