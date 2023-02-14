package com.tngdev.movieappqst.ui.detail

import androidx.lifecycle.ViewModel
import com.tngdev.movieappqst.model.MovieItem
import com.tngdev.movieappqst.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {

    fun updateOnMyWatchList(movieItem: MovieItem, isOnMyWatchList: Boolean) {
        // update here to update in UI through data binding
        movieItem.isOnMyWatchList = !movieItem.isOnMyWatchList
        movieRepository.updateOnMyWatchlist(movieItem, isOnMyWatchList)
    }

}