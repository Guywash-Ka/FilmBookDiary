package com.example.filmbookdiary.repositories

import android.content.Context
import com.example.filmbookdiary.data.Film
import com.example.filmbookdiary.data.FilmData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow

class FilmRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
){
    private val database: FilmData = FilmData

    fun getFilms(): Flow<List<Film>> = database.getFilms()

    suspend fun getFilm(name: String): Film = database.getFilm(name)

    suspend fun addFilm(film: Film) = database.addFilm(film)

    suspend fun removeFilm(film: Film) = database.deleteFilm(film)

    companion object {
        private var INSTANCE: FilmRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = FilmRepository(context)
            }
        }

        fun get(): FilmRepository {
            return INSTANCE ?: throw java.lang.IllegalStateException("Film repository must be initialized")
        }
    }
}