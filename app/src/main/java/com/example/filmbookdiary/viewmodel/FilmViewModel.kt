package com.example.filmbookdiary.viewmodel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.filmbookdiary.data.Film
import com.example.filmbookdiary.data.FilmData

class FilmViewModel: ViewModel() {
    private val _films = getFilms().toMutableStateList()
    val films: List<Film>
    get() = _films

    fun remove() {
        // TODO
    }

    fun add() {
        // TODO
    }

}

private fun getFilms(): List<Film> {
    return FilmData.films
}