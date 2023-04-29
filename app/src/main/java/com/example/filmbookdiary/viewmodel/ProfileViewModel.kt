package com.example.filmbookdiary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmbookdiary.data.Book
import com.example.filmbookdiary.data.Film
import com.example.filmbookdiary.repositories.BookRepository
import com.example.filmbookdiary.repositories.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {

    private val filmRepository = FilmRepository.get()
    private val bookRepository = BookRepository.get()

    private val _films: MutableStateFlow<List<Film>> = MutableStateFlow(emptyList())
    val films: StateFlow<List<Film>>
        get() = _films.asStateFlow()

    private val _books: MutableStateFlow<List<Book>> = MutableStateFlow(emptyList())
    val books: StateFlow<List<Book>>
        get() = _books.asStateFlow()

    fun getNumberOfWatchedFilms(): Flow<Int> {
        return filmRepository.getNumberOfWatchedFilms()
    }

    fun getNumberOfNotWatchedFilms(): Flow<Int> {
        return filmRepository.getNumberOfNotWatchedFilms()
    }

    fun getNumberOfReadBooks(): Flow<Int> {
        return bookRepository.getNumberOfReadBooks()
    }

    fun getNumberOfNotReadBooks(): Flow<Int> {
        return bookRepository.getNumberOfNotReadBooks()
    }

    init {
        viewModelScope.launch {
            filmRepository.getFilms().collect {
                _films.value = it
            }
        }
        viewModelScope.launch {
            bookRepository.getBooks().collect {
                _books.value = it
            }
        }
    }
}