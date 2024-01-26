package com.example.movieapp.presentation.ui.fragments.bottomnavholder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentBottomAppBarHolderBinding
import com.example.movieapp.presentation.ui.fragments.favorites.FavoritesFragment
import com.example.movieapp.presentation.ui.fragments.search.SearchFragment
import com.example.movieapp.presentation.ui.fragments.watchlist.WatchListFragment

class BottomAppBarHolderFragment : Fragment() {

    private lateinit var binding: FragmentBottomAppBarHolderBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_bottom_app_bar_holder,
            container,
            false
        )


        binding.bottomAppBar.setOnItemSelectedListener {menuItem->

            when(menuItem.itemId){
                R.id.action_search -> openFragment(SearchFragment())
                R.id.action_watch_list -> openFragment(WatchListFragment())
                R.id.action_favorites -> openFragment(FavoritesFragment())
            }
            true
        }
        openFragment(SearchFragment())




        return binding.root
    }

    private fun openFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView,fragment)
        fragmentTransaction.commit()
    }

}