package com.example.movieapp.presentation.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.presentation.ui.adapters.PopularMoviesAdapter
import com.example.movieapp.presentation.ui.adapters.TrendingMoviesAdapter
import com.example.movieapp.presentation.ui.adapters.TrendingTVsAdapter
import com.example.movieapp.presentation.ui.adapters.UpComingMoviesAdapter
import com.example.movieapp.presentation.ui.adapters.ViewPagerAdapter
import com.example.movieapp.presentation.ui.viewmodels.MainViewModel
import com.example.movieapp.presentation.ui.viewmodels.MoviesViewModel
import com.example.movieapp.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val comingSoonAdapter by lazy { UpComingMoviesAdapter() }
    private val trendingMoviesAdapter by lazy { TrendingMoviesAdapter() }
    private val trendingTVsAdapter by lazy { TrendingTVsAdapter() }
    private val popularMoviesAdapter by lazy { PopularMoviesAdapter() }
    private val viewPagerAdapter by lazy { ViewPagerAdapter() }


    private lateinit var mainViewModel: MainViewModel
    private lateinit var moviesViewModel: MoviesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)

        setupRecyclerView()
        requestApiData()



        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
    }

    private fun requestApiData() {
        mainViewModel.getMovies(moviesViewModel.applyQueriesTVsAndMovies())

// Trending Movies
        mainViewModel.trendingMoviesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    response.data?.let {
                        trendingMoviesAdapter.setData(it)


                    }
                }

                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    showShimmerEffect()

                }
            }


        }
// Top Rated Movies For ViewPager2
        mainViewModel.topRatedMoviesResponse.observe(viewLifecycleOwner) {response ->
            when(response) {
                is NetworkResult.Success -> {
                    response.data?.let { viewPagerAdapter.submitList(it.results) }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }

        }

// Trending TVs
        mainViewModel.trendingTVsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { trendingTVsAdapter.setData(it) }
                }

                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    showShimmerEffect()

                }
            }
        }


// UpComing Movies
        mainViewModel.upComingMoviesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { comingSoonAdapter.setData(it) }
                }

                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {


                }
            }

        }

// Popular Movies
        mainViewModel.popularMoviesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { popularMoviesAdapter.setData(it) }
                }

                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }

                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }

        }

    }


    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.rVPopular.visibility = View.INVISIBLE
        binding.rVComingSoon.visibility = View.INVISIBLE
        binding.rVTrendingMovies.visibility = View.INVISIBLE
        binding.rVTrendingTV.visibility = View.INVISIBLE

        binding.textViewPopular.visibility = View.INVISIBLE
        binding.textViewComingSoon.visibility = View.INVISIBLE
        binding.textViewTrendingMovie.visibility = View.INVISIBLE
        binding.textViewTrendingTv.visibility = View.INVISIBLE

        binding.shimmerFrameLayout.showShimmer(true)
    }

    private fun hideShimmerEffect() {

        binding.shimmerFrameLayout.visibility = View.INVISIBLE
        binding.rVPopular.visibility = View.VISIBLE
        binding.rVComingSoon.visibility = View.VISIBLE
        binding.rVTrendingMovies.visibility = View.VISIBLE
        binding.rVTrendingTV.visibility = View.VISIBLE

        binding.textViewPopular.visibility = View.VISIBLE
        binding.textViewComingSoon.visibility = View.VISIBLE
        binding.textViewTrendingMovie.visibility = View.VISIBLE
        binding.textViewTrendingTv.visibility = View.VISIBLE

        binding.shimmerFrameLayout.hideShimmer()


    }

    private fun setupRecyclerView() {
        // rv ComingSoon
        binding.rVComingSoon.adapter = comingSoonAdapter
        binding.rVComingSoon.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // rv Trending Movies
        binding.rVTrendingMovies.adapter = trendingMoviesAdapter
        binding.rVTrendingMovies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // rv Trending TVs
        binding.rVTrendingTV.adapter = trendingTVsAdapter
        binding.rVTrendingTV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // rv Popular Movies
        binding.rVPopular.adapter = popularMoviesAdapter
        binding.rVPopular.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Top Rated Movies
        binding.viewPagerTopRated.adapter = viewPagerAdapter

        showShimmerEffect()
    }


}