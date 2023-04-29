package com.example.filmbookdiary.repositories

import android.content.Context
import androidx.room.Room
import com.example.filmbookdiary.data.Film
import com.example.filmbookdiary.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID

private const val DATABASE_NAME = "FBDiary_db"

class FilmRepository @OptIn(DelicateCoroutinesApi::class)
private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
){
    private val database: FilmBookDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            FilmBookDatabase::class.java,
            DATABASE_NAME
        )
        .addMigrations(migration_1_2)
        .addMigrations(migration_2_3)
        .addMigrations(migration_3_4)
        .addMigrations(migration_4_5)
        .addMigrations(migration_5_6)
        .addMigrations(migration_6_7)
        .build()

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