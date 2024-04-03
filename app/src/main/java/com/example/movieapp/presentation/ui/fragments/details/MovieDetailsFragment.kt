package com.example.movieapp.presentation.ui.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.data.models.moviedetails.DetailsResult
import com.example.movieapp.databinding.FragmentDetailsBinding
import com.example.movieapp.presentation.ui.adapters.CastingAdapter
import com.example.movieapp.presentation.ui.viewmodels.FavoritesViewModel
import com.example.movieapp.presentation.ui.viewmodels.MainViewModel
import com.example.movieapp.presentation.ui.viewmodels.MoviesViewModel
import com.example.movieapp.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val castingAdapter by lazy { CastingAdapter() }
    private lateinit var objectMovie: DetailsResult

    private lateinit var mainViewModel: MainViewModel
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var favoritesViewModel: FavoritesViewModel

    private var actionDetailsResult: DetailsResult? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_details, container, false)





        val bundle: MovieDetailsFragmentArgs by navArgs()
        val movieId = bundle.movieId
        val posterPath = bundle.posterPath

        binding.posterPath = posterPath

        CoroutineScope(Dispatchers.Main).launch {
            favoritesViewModel.existsFavorites("userId",movieId)
        }

        lifecycleScope.launch {

            lifecycleScope.launch {
                favoritesViewModel.isFavorite.collect { isFavorite ->
                    updateFavoriteUI(isFavorite)
                }
            }
        }

        binding.imgToolbarBtnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.buttonBookNow.setOnClickListener {
            actionDetailsResult?.let { result ->
                val action =
                    MovieDetailsFragmentDirections.actionDetailsFragmentToBookingScheduleFragment(
                        result
                    )
                Navigation.findNavController(it).navigate(action)
            }
        }

        binding.imgToolbarBtnFav.setOnClickListener {

            favoritesViewModel.handleFavoriteAction(
                "userId",
                movieId,
                objectMovie.originalTitle!!,
                objectMovie.posterPath!!,
                objectMovie.overview!!,
                objectMovie.voteAverage!!
            )
        }



        setupRecyclerView()
        requestApiData(movieId)
        return binding.root
    }

    private fun updateFavoriteUI(isFavorite: Boolean) {
        if (isFavorite) {
            binding.imgToolbarBtnFav.setImageResource(R.drawable.ic_add_fill_fav)
        } else {
            binding.imgToolbarBtnFav.setImageResource(R.drawable.ic_add_favorite)
        }
    }


    private fun requestApiData(movieId: Int) {
        mainViewModel.getCredits(movieId, moviesViewModel.applyQueriesTVsAndMovies())
        // Details
        showProgressBar()

        mainViewModel.movieDetailsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { detailData ->
                        objectMovie = detailData
                        binding.result = detailData
                        actionDetailsResult = detailData
                        binding.category =
                            mainViewModel.processMoviesGenres(detailData.genres ?: emptyList())
                        binding.vote = String.format(Locale.US, "%,.1f", detailData.voteAverage)
                    }
                    hideProgressBar()
                }

                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    hideProgressBar()
                }

                is NetworkResult.Loading -> {
                    showProgressBar()
                }
            }
        }

// Trending Movies
        mainViewModel.movieCreditsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    response.data?.let {
                        castingAdapter.setData(it)
                    }
                    hideProgressBar()
                }

                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    showProgressBar()

                }
            }


        }

    }


    fun setupRecyclerView() {
        binding.rVCast.adapter = castingAdapter

        binding.rVCast.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
    }


}