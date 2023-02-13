package com.tngdev.movieappqst.data

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tngdev.movieappqst.model.MovieItem
import com.tngdev.movieappqst.util.Utils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class LocalMovieDataSource @Inject constructor(
    val application: Application,
    @Named("ioDispatcher")
    val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : MovieDataSource {
    override suspend fun getMovieList(): List<MovieItem> {
        val list = withContext(ioDispatcher) {
            val jsonString = Utils.readFromAsssets("movielist.json", application)
            return@withContext  Gson().fromJson<List<MovieItem>>(
                jsonString,
                object: TypeToken<List<MovieItem>>(){}.type
            )
        }

        return list
    }
}