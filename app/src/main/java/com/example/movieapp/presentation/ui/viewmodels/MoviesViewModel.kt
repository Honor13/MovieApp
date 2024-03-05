package com.example.movieapp.presentation.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.movieapp.util.Constants.Companion.API_KEY

import com.example.movieapp.util.Constants.Companion.QUERY_API_KEY

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    fun applyQueriesTVsAndMovies(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_API_KEY] = API_KEY

        return queries
    }


}