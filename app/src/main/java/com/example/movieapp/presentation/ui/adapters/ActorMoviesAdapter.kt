package com.example.movieapp.presentation.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.database.actormovies.ActorMoviesResult
import com.example.movieapp.data.database.actormovies.Cast
import com.example.movieapp.databinding.ActorMoviesCardLayoutBinding
import com.example.movieapp.presentation.ui.fragments.details.ActorDetailsFragmentDirections
import com.example.movieapp.util.MoviesDiffUtil
import java.util.Locale

class ActorMoviesAdapter: RecyclerView.Adapter<ActorMoviesAdapter.MyViewHolder>() {

    private var actorMovies = emptyList<Cast>()
    class MyViewHolder(private val binding: ActorMoviesCardLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Cast){
            binding.actorMovies = result
            binding.posterPath = result.posterPath
            binding.constLayoutCardView.setOnClickListener {
                //TODO (Onr) adapter içerisine ocItemClickLickListener eklenecek
                if (result.posterPath != null) {
                    val action = ActorDetailsFragmentDirections.actionActorDetailsFragmentToDetailsFragment(result.id,result.posterPath)
                    Navigation.findNavController(it).navigate(action)
                }
            }
            binding.vote = String.format(Locale.US,"%,.1f",result.voteAverage)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ActorMoviesCardLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return actorMovies.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentActorMovies = actorMovies[position]
        holder.bind(currentActorMovies)
    }

    fun setData(newData: ActorMoviesResult) {
        val trendingMoviesDiffUtil = MoviesDiffUtil(actorMovies, newData.cast.take(20))
        val trendingDiffUtilResult = DiffUtil.calculateDiff(trendingMoviesDiffUtil)
        actorMovies = newData.cast.take(20)
        trendingDiffUtilResult.dispatchUpdatesTo(this)
    }
}