package com.example.filmbookdiary.database

import androidx.room.*
import com.example.filmbookdiary.data.Film
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface FilmDao {

    @Query("SELECT * FROM films")
    fun getFilms(): Flow<List<Film>>

    @Query("SELECT * FROM films WHERE id=(:id)")
    suspend fun getFilm(id: UUID): Film

    @Insert
    suspend fun addFilm(film: Film)

    @Delete
    suspend fun removeFilm(film: Film)

    @Query("DELETE FROM films")
    suspend fun removeAllFilms()

    @Update
    suspend fun updateFilm(film: Film)

    @Query("SELECT * FROM films WHERE name LIKE '%' || :filmName || '%'")
    fun searchFilmsByName(filmName: String): Flow<List<Film>>

    @Query("SELECT COUNT(id) FROM films WHERE isWatched = 1")
    fun getNumberOfWatchedFilms(): Flow<Int>

    @Query("SELECT COUNT(id) FROM films WHERE isWatched = 0")
    fun getNumberOfNotWatchedFilms(): Flow<Int>
}