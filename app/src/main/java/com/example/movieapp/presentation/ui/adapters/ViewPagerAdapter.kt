package com.example.movieapp.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.models.movies.Result
import com.example.movieapp.databinding.ViewpagerLayoutBinding
import java.util.Locale

class ViewPagerAdapter() : RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder>() {

    var topRatedMovies = mutableListOf<Result>()

    class MyViewHolder(private var binding: ViewpagerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.result = result
            binding.vote =  String.format(Locale.US,"%,.1f",result.voteAverage)
        }

        companion object {

            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewpagerLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return topRatedMovies.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPopularMovies = topRatedMovies[position]
        holder.bind(currentPopularMovies)
    }

    fun submitList(movies:List<Result>){
        topRatedMovies.addAll(movies)
        this.notifyDataSetChanged()
    }


}