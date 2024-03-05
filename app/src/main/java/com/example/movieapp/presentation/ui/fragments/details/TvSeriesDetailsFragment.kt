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
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentTvSeriesDetailsBinding
import com.example.movieapp.presentation.ui.viewmodels.MainViewModel
import com.example.movieapp.presentation.ui.viewmodels.MoviesViewModel
import com.example.movieapp.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class TvSeriesDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTvSeriesDetailsBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var moviesViewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_tv_series_details,container,false)



        val bundle: TvSeriesDetailsFragmentArgs by navArgs()
        val movieId = bundle.movieId
        val posterPath = bundle.posterPath

        binding.posterPath = posterPath

        requestApiData(movieId)


        binding.imgToolbarBtnBack.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }




        return binding.root
    }

    private fun requestApiData(seriesId: Int) {
        mainViewModel.getTvDetails(seriesId, moviesViewModel.applyQueriesTVsAndMovies())
        // Details
        showProgressBar()

        mainViewModel.tVsDetailsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { detailData ->
                        binding.result = detailData
                        binding.category=mainViewModel.processSeriesGenres(detailData.genres ?: emptyList())
//                        detailData.lastEpisodeToAir.overview
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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
    }
    private fun hideProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

}