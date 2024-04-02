package com.example.movieapp.presentation.ui.fragments.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentFavoritesBinding
import com.example.movieapp.presentation.ui.adapters.FavoriteMoviesAdapter
import com.example.movieapp.presentation.ui.viewmodels.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding : FragmentFavoritesBinding
    private lateinit var favoritesViewModel: FavoritesViewModel

    private val favMoviesAdapter by lazy { FavoriteMoviesAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_favorites,container,false)

        setupRecyclerView()
        favoritesViewModel.getFavorites("userId")
        lifecycleScope.launch {
            favoritesViewModel.listFav.collect{favorites ->
                favMoviesAdapter.setData(favorites)
            }
        }


        return binding.root
    }



    private fun setupRecyclerView() {
        binding.rvFavorites.adapter = favMoviesAdapter
        binding.rvFavorites.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
    }

}