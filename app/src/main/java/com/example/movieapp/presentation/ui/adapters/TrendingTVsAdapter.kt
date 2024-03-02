package com.example.movieapp.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.models.ResultTV
import com.example.movieapp.data.models.TVs
import com.example.movieapp.databinding.TrendingTvCardLayoutBinding
import com.example.movieapp.util.MoviesDiffUtil
import java.util.Locale

class TrendingTVsAdapter:RecyclerView.Adapter<TrendingTVsAdapter.MyViewHolder>() {

    private var trendingTvs = emptyList<ResultTV>()
    class MyViewHolder(private val binding: TrendingTvCardLayoutBinding): RecyclerView.ViewHolder(binding.root) {


        fun bind(resultTV: ResultTV){
            binding.resultTV = resultTV
            binding.vote =  String.format(Locale.US,"%,.1f",resultTV.voteAverage)
            binding.executePendingBindings()
            binding.executePendingBindings()
        }

        companion object {

            fun from(parent: ViewGroup): MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TrendingTvCardLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return trendingTvs.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTVs = trendingTvs[position]
        holder.bind(currentTVs)
    }

    fun setData(newData: TVs) {
        val trendingTVDiffUtil = MoviesDiffUtil(trendingTvs, newData.results)
        val trendingDiffResult = DiffUtil.calculateDiff(trendingTVDiffUtil)
        trendingTvs = newData.results
        trendingDiffResult.dispatchUpdatesTo(this)
    }
}