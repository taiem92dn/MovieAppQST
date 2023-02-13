package com.tngdev.movieappqst.repository

import com.tngdev.movieappqst.data.LocalMovieDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    val localMovieDataSource: LocalMovieDataSource
) {
    suspend fun getMovieList() = localMovieDataSource.getMovieList()
}