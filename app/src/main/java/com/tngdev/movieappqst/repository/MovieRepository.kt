package com.tngdev.movieappqst.repository

import com.tngdev.movieappqst.data.LocalMovieDataSource
import com.tngdev.movieappqst.data.SortBy
import com.tngdev.movieappqst.model.MovieItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    val localMovieDataSource: LocalMovieDataSource
) {
    suspend fun getMovieList() = localMovieDataSource.getMovieList()

    fun setSortBy(sortBy: SortBy) = localMovieDataSource.setSortBy(sortBy)

    fun updateOnMyWatchlist(movieItem: MovieItem, isOnMyWatchList: Boolean) =
        localMovieDataSource.updateOnMyWatchList(movieItem, isOnMyWatchList)
}