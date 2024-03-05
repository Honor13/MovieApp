package com.example.movieapp.presentation.ui.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentDetailsBinding
import com.example.movieapp.presentation.ui.adapters.CastingAdapter
import com.example.movieapp.presentation.ui.viewmodels.MainViewModel
import com.example.movieapp.presentation.ui.viewmodels.MoviesViewModel
import com.example.movieapp.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val castingAdapter by lazy { CastingAdapter() }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var moviesViewModel: MoviesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_details, container, false)


        val bundle: DetailsFragmentArgs by navArgs()
        val movieId = bundle.movieId
        val posterPath= bundle.posterPath
        binding.posterPath = posterPath

//        binding.vote = String.format(Locale.US, "%,.1f", result.voteAverage)

        binding.imgToolbarBtnBack.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }

        setupRecyclerView()
        requestApiData(movieId)
        return binding.root
    }


    private fun requestApiData(movieId: Int) {
        mainViewModel.getCredits(movieId, moviesViewModel.applyQueriesTVsAndMovies())
        // Details
        showProgressBar()

        mainViewModel.movieDetailsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { detailData ->
                        binding.result = detailData
                        binding.category=mainViewModel.processGenres(detailData.genres ?: emptyList())
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
    }

}