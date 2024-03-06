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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentActorDetailsBinding
import com.example.movieapp.presentation.ui.adapters.ActorMoviesAdapter
import com.example.movieapp.presentation.ui.viewmodels.MainViewModel
import com.example.movieapp.presentation.ui.viewmodels.MoviesViewModel
import com.example.movieapp.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActorDetailsFragment : Fragment() {

    private lateinit var binding: FragmentActorDetailsBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var moviesViewModel: MoviesViewModel
    private val actorMoviesAdapter by lazy { ActorMoviesAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_actor_details,
            container,
            false
        )

        val Bundle: ActorDetailsFragmentArgs by navArgs()
        val personId = Bundle.personId
        val posterPath = Bundle.posterPath
        binding.posterPath = posterPath

        setupRecyclerView()
        requestApiData(personId)

        binding.imgToolbarBtnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }




        return binding.root
    }

    fun requestApiData(personId:Int){
        mainViewModel.getActorDetails(personId, moviesViewModel.applyQueriesTVsAndMovies())
        mainViewModel.getActorMovies(personId,moviesViewModel.applyQueriesTVsAndMovies())

        mainViewModel.actorDetailsResponse.observe(viewLifecycleOwner) { response ->

            when (response) {
                is NetworkResult.Loading -> {
                    showProgressBar()
                }

                is NetworkResult.Success -> {
                    response.data?.let { actorData ->
                        binding.actor = actorData
                    }
                    hideProgressBar()
                }

                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    hideProgressBar()
                }
            }

        }

        mainViewModel.actorMoviesResponse.observe(viewLifecycleOwner){response->

            when(response){
                is NetworkResult.Loading -> {
                    showProgressBar()
                }
                is NetworkResult.Success -> {
                    response.data?.let {
                        actorMoviesAdapter.setData(it)
                    }
                    hideProgressBar()
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    hideProgressBar()
                }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
    }

    fun showProgressBar() {
        binding.progressBarActorDetails.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        binding.progressBarActorDetails.visibility = View.INVISIBLE
    }

    fun setupRecyclerView(){
        binding.rvActorMovies.adapter = actorMoviesAdapter
        binding.rvActorMovies.layoutManager = GridLayoutManager(requireContext(), 3)

    }


}