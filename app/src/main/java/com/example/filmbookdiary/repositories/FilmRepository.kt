package com.example.filmbookdiary.repositories

import android.content.Context
import androidx.room.Room
import com.example.filmbookdiary.data.Film
import com.example.filmbookdiary.database.FilmDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import java.util.UUID

private const val DATABASE_NAME = "FBDiary_db"

class FilmRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
){
    private val database: FilmDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            FilmDatabase::class.java,
            DATABASE_NAME
        )
        .build()

    fun getFilms(): Flow<List<Film>> = database.filmDao().getFilms()

    suspend fun getFilm(id: UUID): Film = database.filmDao().getFilm(id)

    suspend fun addFilm(film: Film) = database.filmDao().addFilm(film)

    suspend fun removeFilm(film: Film) = database.filmDao().removeFilm(film)

    companion object {
        private var INSTANCE: FilmRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = FilmRepository(context)
            }
        }

        fun get(): FilmRepository {
            return INSTANCE ?: throw java.lang.IllegalStateException("FilmRepository must be initialized")
        }
    }
}