package com.example.movieapp.presentation.ui.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.models.actormovies.ActorMoviesResult
import com.example.movieapp.data.models.actordetails.ActorDetails
import com.example.movieapp.data.models.movies.Movies
import com.example.movieapp.data.models.tv.TVs
import com.example.movieapp.data.models.moviecredits.Credits
import com.example.movieapp.data.models.moviedetails.DetailsResult
import com.example.movieapp.data.models.moviedetails.Genre
import com.example.movieapp.data.models.tvDetails.DetailsTVResult
import com.example.movieapp.data.models.tvcredits.TvCredits
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
    val movieDetailsResponse: MutableLiveData<NetworkResult<DetailsResult>> = MutableLiveData()
    val tVsDetailsResponse: MutableLiveData<NetworkResult<DetailsTVResult>> = MutableLiveData()
    val tVsCreditsResponse: MutableLiveData<NetworkResult<TvCredits>> = MutableLiveData()
    val actorDetailsResponse: MutableLiveData<NetworkResult<ActorDetails>> = MutableLiveData()
    val actorMoviesResponse: MutableLiveData<NetworkResult<ActorMoviesResult>> = MutableLiveData()

    fun getMovies(queries: Map<String, String>) = viewModelScope.launch {
        getMoviesSafeCall(queries)
    }

    fun getCredits(movieId: Int, queries: Map<String, String>) = viewModelScope.launch {
        getMoviesCreditsAndDetailsSafeCall(movieId, queries)
    }

    fun getTvDetails(seriesId: Int, queries: Map<String, String>) = viewModelScope.launch {
        getTvSeriesDetailsSafeCall(seriesId, queries)
    }

    fun getTvCredits(seriesId: Int, queries: Map<String, String>) = viewModelScope.launch {
        getTvSeriesCreditsSafeCall(seriesId, queries)
    }

    fun getActorDetails(personId: Int, queries: Map<String, String>) = viewModelScope.launch {
        getActorDetailsSafeCall(personId, queries)
    }

    fun getActorMovies(personId: Int, queries: Map<String, String>) = viewModelScope.launch {
        getActorMoviesResponseSafeCall(personId,queries)
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

    // TODO (Onur) All HandleResponse Functions will be organized as generic Type :)
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

    private fun handleMovieDetailsResponse(response: Response<DetailsResult>): NetworkResult<DetailsResult> {
        when {
            response.code() == 504 -> {
                return NetworkResult.Error("Your request to the backend server timed out. Try again.")
            }

            response.code() == 429 -> {
                return NetworkResult.Error("API key limited")
            }

            response.isSuccessful -> {
                val movieDetails = response.body()
                return NetworkResult.Success(movieDetails!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun handleTvSeriesResponse(response: Response<DetailsTVResult>): NetworkResult<DetailsTVResult> {
        when {
            response.code() == 504 -> {
                return NetworkResult.Error("Your request to the backend server timed out. Try again.")
            }

            response.code() == 429 -> {
                return NetworkResult.Error("API key limited")
            }

            response.isSuccessful -> {
                val serisDetails = response.body()
                return NetworkResult.Success(serisDetails!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun handleTvCreditsResponse(response: Response<TvCredits>): NetworkResult<TvCredits> {
        when {
            response.code() == 504 -> {
                return NetworkResult.Error("Your request to the backend server timed out. Try again.")
            }

            response.code() == 429 -> {
                return NetworkResult.Error("API key limited")
            }

            response.isSuccessful -> {
                val seriesCredits = response.body()
                return NetworkResult.Success(seriesCredits!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun handleActorDetailsResponse(response: Response<ActorDetails>): NetworkResult<ActorDetails> {
        when {
            response.code() == 504 -> {
                return NetworkResult.Error("Your request to the backend server timed out. Try again.")
            }

            response.code() == 429 -> {
                return NetworkResult.Error("API key limited")
            }

            response.isSuccessful -> {
                val actorDetails = response.body()
                return NetworkResult.Success(actorDetails!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }
    private fun handleActorMoviesResponse(response: Response<ActorMoviesResult>): NetworkResult<ActorMoviesResult> {
        when {
            response.code() == 504 -> {
                return NetworkResult.Error("Your request to the backend server timed out. Try again.")
            }

            response.code() == 429 -> {
                return NetworkResult.Error("API key limited")
            }

            response.isSuccessful -> {
                val actorMovies = response.body()
                return NetworkResult.Success(actorMovies!!)
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

    private suspend fun getMoviesCreditsAndDetailsSafeCall(
        movieId: Int,
        queries: Map<String, String>
    ) {
        movieCreditsResponse.value = NetworkResult.Loading()
        movieDetailsResponse.value = NetworkResult.Loading()


        if (hasInternetConnection()) {
            try {
                val responseMovieCredits = repository.remote.getMovieCredits(movieId, queries)
                val responseMovieDetails = repository.remote.getMovieDetails(movieId, queries)

                movieDetailsResponse.value = handleMovieDetailsResponse(responseMovieDetails)
                movieCreditsResponse.value = handleCreditsResponse(responseMovieCredits)

            } catch (e: Exception) {
                movieCreditsResponse.value =
                    NetworkResult.Error("There was a problem fetching Credits data")
            }
        }
    }


    private suspend fun getTvSeriesDetailsSafeCall(seriesId: Int, queries: Map<String, String>) {
        tVsDetailsResponse.value = NetworkResult.Loading()

        if (hasInternetConnection()) {

            try {
                val responseTvDetails = repository.remote.getTvDetails(seriesId, queries)

                tVsDetailsResponse.value = handleTvSeriesResponse(responseTvDetails)
            } catch (e: Exception) {
                tVsDetailsResponse.value =
                    NetworkResult.Error("There was a problem fetching TvSeries data")
            }

        }

    }

    private suspend fun getTvSeriesCreditsSafeCall(seriesId: Int, queries: Map<String, String>) {

        tVsCreditsResponse.value = NetworkResult.Loading()

        if (hasInternetConnection()) {
            try {
                val responseTvCredits = repository.remote.getTvCredits(seriesId, queries)

                tVsCreditsResponse.value = handleTvCreditsResponse(responseTvCredits)
            } catch (e: Exception) {
                tVsCreditsResponse.value =
                    NetworkResult.Error("There was a problem fetching TvCredits data")
            }
        }
    }

    private suspend fun getActorDetailsSafeCall(personId: Int, queries: Map<String, String>) {

        actorDetailsResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val responseActorDetails = repository.remote.getActorDetails(personId,queries)

                actorDetailsResponse.value =handleActorDetailsResponse(responseActorDetails)
            } catch (e: Exception) {
                actorDetailsResponse.value = NetworkResult.Error("There was a problem fetching actorDetails data")
            }
        }
    }

    private suspend fun getActorMoviesResponseSafeCall(personId: Int, queries: Map<String, String>) {

        actorMoviesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val responseActorMovies = repository.remote.getActorMovies(personId,queries)

                actorMoviesResponse.value = handleActorMoviesResponse(responseActorMovies)
            } catch (e: Exception) {
               actorMoviesResponse.value = NetworkResult.Error("There was a problem fetching actorMovies data")
            }
        }
    }


    fun processMoviesGenres(genresList: List<Genre>): String {
        return genresList.joinToString(", ") { it.name }
    }

    fun processSeriesGenres(genresList: List<com.example.movieapp.data.models.tvDetails.Genre>): String {
        return genresList.joinToString(", ") { it.name }
    }
}