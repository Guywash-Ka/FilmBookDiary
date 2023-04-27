package com.example.filmbookdiary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.filmbookdiary.data.Film
import com.example.filmbookdiary.repositories.FilmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class SingleFilmViewModel(filmId: UUID): ViewModel() {
    private val filmRepository = FilmRepository.get()

    private val _film: MutableStateFlow<Film?> = MutableStateFlow(null)
    val film: StateFlow<Film?>
    get() = _film.asStateFlow()

    init {
        viewModelScope.launch {
            _film.value = filmRepository.getFilm(filmId)
        }
    }

    fun updateFilm(onUpdate: (Film) -> Film) {
        _film.update { oldFilm ->
            oldFilm?.let { onUpdate(it) }
        }
        film.value?.let { filmRepository.updateFilm(it) }
    }

    suspend fun removeFilm(film: Film) {
        filmRepository.removeFilm(film)
    }

    override fun onCleared() {
        super.onCleared()
        film.value?.let { filmRepository.updateFilm(it) }
    }
}

class SingFilmViewModelFactory(
    private val filmId: UUID
) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return SingleFilmViewModel(filmId) as T
    }
}