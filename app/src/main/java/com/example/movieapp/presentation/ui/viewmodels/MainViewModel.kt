package com.example.movieapp.presentation.ui.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.models.Movies
import com.example.movieapp.data.models.TVs
import com.example.movieapp.data.models.moviedetails.Cast
import com.example.movieapp.data.models.moviedetails.Credits
import com.example.movieapp.data.network.Repository
import com.example.movieapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    val upComingMoviesResponse: MutableLiveData<NetworkResult<Movies>> = MutableLiveData()
    val trendingMoviesResponse: MutableLiveData<NetworkResult<Movies>> = MutableLiveData()
    val trendingTVsResponse: MutableLiveData<NetworkResult<TVs>> = MutableLiveData()
    val popularMoviesResponse: MutableLiveData<NetworkResult<Movies>> = MutableLiveData()
    val topRatedMoviesResponse: MutableLiveData<NetworkResult<Movies>> = MutableLiveData()
    val movieCreditsResponse: MutableLiveData<NetworkResult<Credits>> = MutableLiveData()

    fun getMovies(queries: Map<String, String>) = viewModelScope.launch {
        getMoviesSafeCall(queries)
    }

    fun getCredits(movieId: Int, queries: Map<String, String>) = viewModelScope.launch {
        getMoviesCreditsSafeCall(movieId, queries)
    }


    fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


    private fun handleMoviesResponse(response: Response<Movies>): NetworkResult<Movies> {

        when {
            response.code() == 504 -> {
                return NetworkResult.Error("Your request to the backend server timed out. Try again.")
            }

            response.code() == 429 -> {
                return NetworkResult.Error("API key limited")
            }

            response.isSuccessful -> {
                val movies = response.body()
                return NetworkResult.Success(movies!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }


        }


    }

    private fun handleTVsResponse(response: Response<TVs>): NetworkResult<TVs> {

        when {
            response.code() == 504 -> {
                return NetworkResult.Error("Your request to the backend server timed out. Try again.")
            }

            response.code() == 429 -> {
                return NetworkResult.Error("API key limited")
            }

            response.isSuccessful -> {
                val movies = response.body()
                return NetworkResult.Success(movies!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }


        }


    }

    private fun handleCreditsResponse(response: Response<Credits>): NetworkResult<Credits> {
        when {
            response.code() == 504 -> {
                return NetworkResult.Error("Your request to the backend server timed out. Try again.")
            }

            response.code() == 429 -> {
                return NetworkResult.Error("API key limited")
            }

            response.isSuccessful -> {
                val credits = response.body()
                val filteredCast = credits?.cast?.filter { it.knownForDepartment == "Acting" }
                if (filteredCast != null && filteredCast.isNotEmpty()) {
                    val filteredCredits = Credits(filteredCast, credits.crew, credits.id)
                    return@handleCreditsResponse NetworkResult.Success(filteredCredits)
                } else {
                    return@handleCreditsResponse NetworkResult.Error("No cast members with name 'Acting' found")
                }
            }

            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }



        private suspend fun getMoviesSafeCall(queries: Map<String, String>) {

            upComingMoviesResponse.value = NetworkResult.Loading()
            trendingMoviesResponse.value = NetworkResult.Loading()
            trendingTVsResponse.value = NetworkResult.Loading()
            popularMoviesResponse.value = NetworkResult.Loading()
            topRatedMoviesResponse.value = NetworkResult.Loading()

            if (hasInternetConnection()) {
                try {
                    val responseUpComing = repository.remote.getUpComingMovies(queries)
                    val responseTrendingMovies = repository.remote.getTrendingMovies(queries)
                    val responseTVsMovies = repository.remote.getTrendingTVs(queries)
                    val responsePopularMovies = repository.remote.getPopularMovies(queries)
                    val responseTopRatedMovies = repository.remote.getTopRatedMovies(queries)

                    upComingMoviesResponse.value = handleMoviesResponse(responseUpComing)
                    trendingMoviesResponse.value = handleMoviesResponse(responseTrendingMovies)
                    trendingTVsResponse.value = handleTVsResponse(responseTVsMovies)
                    popularMoviesResponse.value = handleMoviesResponse(responsePopularMovies)
                    topRatedMoviesResponse.value = handleMoviesResponse(responseTopRatedMovies)

                } catch (e: Exception) {
                    upComingMoviesResponse.value =
                        NetworkResult.Error("Up Coming Movies Not Found!")
                    trendingMoviesResponse.value = NetworkResult.Error("Tending Movies Not Found")
                    trendingTVsResponse.value = NetworkResult.Error("Popular Movies Not Found")
                    trendingTVsResponse.value = NetworkResult.Error("Tending Movies Not Found")
                    topRatedMoviesResponse.value = NetworkResult.Error("Top Rated Movies Not Found")
                }
            } else {
                upComingMoviesResponse.value = NetworkResult.Error("No Internet Connection")
            }

        }

        private suspend fun getMoviesCreditsSafeCall(movieId: Int, queries: Map<String, String>) {
            movieCreditsResponse.value = NetworkResult.Loading()


            if (hasInternetConnection()) {
                try {
                    val responseMovieCredits = repository.remote.getMovieCredits(movieId, queries)

                    movieCreditsResponse.value = handleCreditsResponse(responseMovieCredits)
                } catch (e: Exception) {
                    movieCreditsResponse.value =
                        NetworkResult.Error("There was a problem fetching Credits data")
                }
            }
        }


    }