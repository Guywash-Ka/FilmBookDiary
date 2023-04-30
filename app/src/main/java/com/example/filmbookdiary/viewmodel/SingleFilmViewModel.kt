package com.example.filmbookdiary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmbookdiary.data.Film
import com.example.filmbookdiary.repositories.FilmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SingleFilmViewModel @Inject constructor(
    private val filmRepository: FilmRepository
): ViewModel() {
    private val _film: MutableStateFlow<Film?> = MutableStateFlow(null)
    val film: StateFlow<Film?>
        get() = _film.asStateFlow()

    suspend fun getFilm(id: UUID) {
        viewModelScope.launch {
            _film.value = filmRepository.getFilm(id)
        }
    }

    suspend fun updateFilm(onUpdate: (Film) -> Film) {
        _film.update { oldFilm ->
            oldFilm?.let { onUpdate(it) }
        }
        film.value?.let { filmRepository.updateFilm(it) }
    }

    suspend fun removeFilm(film: Film) {
        filmRepository.removeFilm(film)
    }
}