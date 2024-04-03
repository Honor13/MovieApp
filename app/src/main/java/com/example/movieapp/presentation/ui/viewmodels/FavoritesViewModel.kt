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

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    // Favori durumunu güncelleyen fonksiyon
    fun updateFavoriteStatus(isFavorite: Boolean) {
        _isFavorite.value = isFavorite
    }


    fun addFavoriteMovies(
        userId: String,
        movieId: Int,
        movieName: String,
        moviePosterPath: String,
        movieOverview: String,
        rating: Double
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            favoritesRepository.addFavorites(
                userId,
                movieId,
                movieName,
                moviePosterPath,
                movieOverview,
                rating
            )
        }
    }


    fun getFavorites(userId: String) {
        viewModelScope.launch {
            favoritesRepository.getFavoritesByUserId(userId).collect { favorites ->
                _listFav.value = favorites
            }
        }
    }

    fun deleteFavorites(userId: String, movieId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            favoritesRepository.deleteFavorites(userId, movieId)
        }
    }

    suspend fun existsFavorites(userId: String, movieId: Int): Boolean {
        _isFavorite.value = favoritesRepository.existsFavorite(userId, movieId)
        return _isFavorite.value!!
    }

    fun handleFavoriteAction(
        userId: String,
        movieId: Int,
        orginalTitle: String,
        posterPath: String,
        overview: String,
        voteAvarage: Double
    ) {
        viewModelScope.launch {
            val isFavorite = existsFavorites(userId, movieId)

            if (isFavorite) {
                favoritesRepository.deleteFavorites(userId, movieId)
                _isFavorite.value = false
            } else {
                addFavoriteMovies(
                    "userId",
                    movieId,
                    orginalTitle,
                    posterPath,
                    overview,
                    voteAvarage
                )
                _isFavorite.value = true
            }

        }
    }


}