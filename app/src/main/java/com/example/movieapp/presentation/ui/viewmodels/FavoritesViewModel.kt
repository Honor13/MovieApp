package com.example.movieapp.presentation.ui.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.database.entities.Favorites
import com.example.movieapp.data.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _listFav = MutableStateFlow<List<Favorites>>(emptyList())
    val listFav: StateFlow<List<Favorites>> = _listFav.asStateFlow()

    fun addFavoriteMovies(userId: String,movieId:Int,movieName:String,moviePosterPath: String,movieOverview: String,rating: Double){
        CoroutineScope(Dispatchers.Main).launch{
            favoritesRepository.addFavorites(userId, movieId, movieName, moviePosterPath, movieOverview, rating)
        }
    }


    fun getFavorites(userId: String){
        viewModelScope.launch {
            favoritesRepository.getFavoritesByUserId(userId).collect {favorites->
                _listFav.value = favorites
            }
        }
    }



}