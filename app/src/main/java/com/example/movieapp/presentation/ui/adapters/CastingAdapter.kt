package com.example.movieapp.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.models.moviecredits.Cast
import com.example.movieapp.data.models.moviecredits.Credits
import com.example.movieapp.databinding.CastCardLayoutBinding
import com.example.movieapp.util.MoviesDiffUtil

class CastingAdapter:RecyclerView.Adapter<CastingAdapter.MyViewHolder>(){
    private var castings = emptyList<Cast>()
    class MyViewHolder(private val binding: CastCardLayoutBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(credits: Cast){
            binding.castResult = credits
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup):MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CastCardLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return castings.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val currentCasts = castings[position]
        holder.bind(currentCasts)
    }

    fun setData(newData: Credits) {
        val trendingMoviesDiffUtil = MoviesDiffUtil(castings, newData.crew)
        val trendingDiffUtilResult = DiffUtil.calculateDiff(trendingMoviesDiffUtil)
        castings = newData.cast
        trendingDiffUtilResult.dispatchUpdatesTo(this)
    }
}