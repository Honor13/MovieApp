package com.example.movieapp.presentation.ui.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentActorDetailsBinding


class ActorDetailsFragment : Fragment() {

    private lateinit var binding: FragmentActorDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_actor_details,container,false)

        val Bundle: ActorDetailsFragmentArgs by navArgs()
        val id = Bundle.personId


        binding.imgToolbarBtnBack.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }

        return binding.root
    }



}