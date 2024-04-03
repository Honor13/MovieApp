package com.example.movieapp.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.database.entities.Favorites
import com.example.movieapp.databinding.FavoritesCardDesignBinding
import com.example.movieapp.util.MoviesDiffUtil

class FavoriteMoviesAdapter(private val onItemClickListener: (Favorites,Int) -> Unit) : RecyclerView.Adapter<FavoriteMoviesAdapter.MyViewHolder>() {

    private var favMovies = emptyList<Favorites>()
    class MyViewHolder(private val binding: FavoritesCardDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favorites: Favorites,onItemClickListener: (Favorites,Int) -> Unit) {
            binding.favorites = favorites

            binding.imageBookmark.setOnClickListener {
                onItemClickListener.invoke(favorites,favorites.movieId)
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoritesCardDesignBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return favMovies.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentFavoriteMovies = favMovies[position]
        holder.bind(currentFavoriteMovies,onItemClickListener)
    }

    fun setData(newData: List<Favorites>){
        val favDiffUtil = MoviesDiffUtil(favMovies,newData)
        val diffUtilResult = DiffUtil.calculateDiff(favDiffUtil)
        favMovies = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}