package com.example.filmbookdiary.viewmodel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmbookdiary.data.Film
import com.example.filmbookdiary.data.FilmData
import com.example.filmbookdiary.repositories.FilmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FilmViewModel: ViewModel() {

    private val filmRepository = FilmRepository.get()

    private val _films: MutableStateFlow<List<Film>> = MutableStateFlow(emptyList())
    val films: StateFlow<List<Film>>
    get() = _films.asStateFlow()

    suspend fun addFilm(film: Film) {
        filmRepository.addFilm(film)
    }

    suspend fun removeFilm(film: Film) {
        filmRepository.removeFilm(film)
    }

    init {
        viewModelScope.launch {
            filmRepository.getFilms().collect {
                _films.value = it
            }
        }
    }
}