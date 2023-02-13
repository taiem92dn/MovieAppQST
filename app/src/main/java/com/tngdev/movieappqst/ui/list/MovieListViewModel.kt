package com.tngdev.movieappqst.ui.list

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tngdev.movieappqst.model.MovieItem
import com.tngdev.movieappqst.repository.MovieRepository
import com.tngdev.movieappqst.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MovieListViewModel @Inject constructor(
    val app: Application,
    val movieRepository: MovieRepository,
): ViewModel() {

    private val _movieList = MutableLiveData<List<MovieItem>>()
    val movieList: LiveData<List<MovieItem>> = _movieList

    init {
        loadMovieList()
    }

    private fun loadMovieList() {
        viewModelScope.launch {
            _movieList.value = movieRepository.getMovieList()
        }
    }

}