package com.tngdev.movieappqst.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tngdev.movieappqst.R
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

    private val imgResArray = intArrayOf(
        R.drawable.tenet,
        R.drawable.spider_man,
        R.drawable.knives_out,
        R.drawable.guardians_of_the_galaxy,
        R.drawable.avengers
    )

    private val _movieListFlow = MutableStateFlow<List<MovieItem>>(listOf())

    override suspend fun getMovieList(): Flow<List<MovieItem>> {

        // get the indexes were saved from SharePreference
        val sharePreferences = application.getSharedPreferences("movie", 0)
        val idxStr = sharePreferences.getString("watchlist", "")
        val idxList = if (idxStr?.length == 0) {
            listOf()
        }
        else {
            idxStr?.split(" ")?.map {
                it.toInt()
            } ?: listOf()
        }

        // fetch list for the first time
        if (_movieListFlow.value.isEmpty()) {
            val list = withContext(ioDispatcher) {
                val jsonString = Utils.readFromAsssets("movielist.json", application)
                return@withContext  Gson().fromJson<List<MovieItem>>(
                    jsonString,
                    object: TypeToken<List<MovieItem>>(){}.type
                )
            }

            _movieListFlow.value = list.mapIndexed { index, movieItem ->
                movieItem.apply {
                    imageRes = imgResArray[index]

                    if (idxList.indexOf(index) != -1) {
                        isOnMyWatchList = true
                    }
                }
            }
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

        // save the current indexes in OnMyWatchList into SharePreference
        val sharePreferences = application.getSharedPreferences("movie", 0)
        var idxStr = StringBuilder()
        for (i in 0 until _movieListFlow.value.size) {
            if (_movieListFlow.value[i].isOnMyWatchList) {
                idxStr.append(i)
                idxStr.append(" ")
            }
        }


        sharePreferences.edit()
            .putString("watchlist", idxStr.trim().toString())
            .apply()

    }


}