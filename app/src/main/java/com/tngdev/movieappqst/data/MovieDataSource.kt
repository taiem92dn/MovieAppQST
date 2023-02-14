package com.tngdev.movieappqst.data

import com.tngdev.movieappqst.model.MovieItem
import kotlinx.coroutines.flow.Flow

interface MovieDataSource {
    suspend fun getMovieList() : Flow<List<MovieItem>>

    fun setSortBy(sortBy: SortBy)

    fun updateOnMyWatchList(movieItem: MovieItem, isOnMyWatchList: Boolean)
}

enum class SortBy {
    Title,
    ReleasedDate,
    None
}
