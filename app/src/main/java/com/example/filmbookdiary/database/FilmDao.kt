package com.example.filmbookdiary.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
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

}