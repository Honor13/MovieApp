package com.example.movieapp.presentation.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_home,container,false)

        showShimmer()

        return binding.root
    }



    private fun showShimmer(){
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
//    private fun hideShimmer(){
//
//        binding.shimmerFrameLayout.visibility = View.INVISIBLE
//        binding.rVPopular.visibility = View.VISIBLE
//        binding.rVComingSoon.visibility = View.VISIBLE
//        binding.rVTrendingMovies.visibility = View.VISIBLE
//        binding.rVTrendingTV.visibility = View.VISIBLE
//        binding.shimmerFrameLayout.hideShimmer()
//
//
//    }
}