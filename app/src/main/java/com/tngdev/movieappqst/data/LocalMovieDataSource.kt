package com.tngdev.movieappqst.data

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tngdev.movieappqst.model.MovieItem
import com.tngdev.movieappqst.util.Utils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class LocalMovieDataSource @Inject constructor(
    val application: Application,
    @Named("ioDispatcher")
    val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : MovieDataSource {


    private val _movieListFlow = MutableStateFlow<List<MovieItem>>(listOf())

    override suspend fun getMovieList(): Flow<List<MovieItem>> {
        // fetch list for the first time
        if (_movieListFlow.value.isEmpty()) {
            val list = withContext(ioDispatcher) {
                val jsonString = Utils.readFromAsssets("movielist.json", application)
                return@withContext  Gson().fromJson<List<MovieItem>>(
                    jsonString,
                    object: TypeToken<List<MovieItem>>(){}.type
                )
            }

            _movieListFlow.value = list
        }

        return _movieListFlow
    }


    override fun setSortBy(sortBy: SortBy) {
        when(sortBy) {
            SortBy.ReleasedDate -> {
                _movieListFlow.value = _movieListFlow.value.sortedBy { it.releasedDate }
            }
            SortBy.Title -> {
                _movieListFlow.value = _movieListFlow.value.sortedBy { it.title }
            }
            else -> {

            }
        }
    }

    override fun updateOnMyWatchList(movieItem: MovieItem, isOnMyWatchList: Boolean) {
        val idx = _movieListFlow.value.indexOf(movieItem)
        if (idx != -1) {
            _movieListFlow.value[idx].isOnMyWatchList = isOnMyWatchList
        }

    }


}