package com.tngdev.movieappqst.ui.list

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tngdev.movieappqst.data.SortBy
import com.tngdev.movieappqst.model.MovieItem
import com.tngdev.movieappqst.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieListViewModel @Inject constructor(
    val app: Application,
    private val movieRepository: MovieRepository,
): ViewModel() {

    private val _movieList = MutableLiveData<List<MovieItem>>()
    val movieList: LiveData<List<MovieItem>> = _movieList

    private val _sortByState = MutableLiveData(SortBy.None)
    val sortByState: LiveData<SortBy> = _sortByState

    init {
        loadMovieList()
    }

    private fun loadMovieList() {
        viewModelScope.launch {
            movieRepository.getMovieList().collect {
                _movieList.value = it
            }
        }
    }

    fun setSortBy(sortBy: SortBy) {
        _sortByState.value = sortBy
        movieRepository.setSortBy(sortBy)
    }
}