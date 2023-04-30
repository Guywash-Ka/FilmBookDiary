package com.example.filmbookdiary.repositories

import com.example.filmbookdiary.data.Film
import com.example.filmbookdiary.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class FilmRepository @Inject constructor(
    val database: FilmBookDatabase,
){
    @OptIn(DelicateCoroutinesApi::class)
    private val coroutineScope: CoroutineScope = GlobalScope
    fun getFilms(): Flow<List<Film>> = database.filmDao().getFilms()

    suspend fun getFilm(id: UUID): Film = database.filmDao().getFilm(id)

    suspend fun addFilm(film: Film) = database.filmDao().addFilm(film)

    suspend fun removeFilm(film: Film) = database.filmDao().removeFilm(film)

    suspend fun removeAllFilms() = database.filmDao().removeAllFilms()

    fun getNumberOfWatchedFilms() = database.filmDao().getNumberOfWatchedFilms()
    fun getNumberOfNotWatchedFilms() = database.filmDao().getNumberOfNotWatchedFilms()

    fun updateFilm(film: Film) {
        coroutineScope.launch {
            database.filmDao().updateFilm(film)
        }
    }

    fun searchFilmsByName(name: String): Flow<List<Film>> = database.filmDao().searchFilmsByName(name)
}