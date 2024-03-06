package com.example.movieapp.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.models.tvcredits.Cast
import com.example.movieapp.data.models.tvcredits.TvCredits
import com.example.movieapp.databinding.TvCastCardLayoutBinding
import com.example.movieapp.util.MoviesDiffUtil

class TvSeriesCastingAdapter(): RecyclerView.Adapter<TvSeriesCastingAdapter.MyViewHolder>() {

    private var castings = emptyList<Cast>()
    class MyViewHolder(private val binding: TvCastCardLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(cast: Cast){
            binding.tvCastResult = cast
            binding.executePendingBindings()

        }
        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TvCastCardLayoutBinding.inflate(layoutInflater,parent,false)
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

    fun setData(newData: TvCredits) {
        val trendingMoviesDiffUtil = MoviesDiffUtil(castings, newData.crew)
        val trendingDiffUtilResult = DiffUtil.calculateDiff(trendingMoviesDiffUtil)
        castings = newData.cast
        trendingDiffUtilResult.dispatchUpdatesTo(this)
    }
}