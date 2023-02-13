package com.tngdev.movieappqst.data

import com.tngdev.movieappqst.model.MovieItem

interface MovieDataSource {
    suspend fun getMovieList() : List<MovieItem>
}