package com.example.movieapp.presentation.ui.fragments.booknow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentBookNowBinding
import java.util.Locale


class BookNowFragment : Fragment() {

    private lateinit var binding: FragmentBookNowBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_book_now, container, false)

        val bundle: BookNowFragmentArgs by navArgs()
        val movie = bundle.detailsResult
        val date = bundle.date
        val theaterName = bundle.movieThaeterName
        val time = bundle.chipTime
        binding.movie = movie
        binding.vote = String.format(Locale.US, "%,.1f", movie.voteAverage)
        binding.date = date
        binding.movieTheaterName = theaterName
        binding.time = time


        return binding.root
    }


}