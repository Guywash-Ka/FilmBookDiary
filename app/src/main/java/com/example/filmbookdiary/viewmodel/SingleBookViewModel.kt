package com.example.filmbookdiary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmbookdiary.data.Book
import com.example.filmbookdiary.repositories.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SingleBookViewModel @Inject constructor(
    private val bookRepository: BookRepository
): ViewModel() {

    private val _book: MutableStateFlow<Book?> = MutableStateFlow(null)
    val book: StateFlow<Book?>
        get() = _book.asStateFlow()

    suspend fun getBook(id: UUID) {
        viewModelScope.launch {
            _book.value = bookRepository.getBook(id)
        }
    }

    suspend fun updateBook(onUpdate: (Book) -> Book) {
        _book.update { oldBook ->
            oldBook?.let { onUpdate(it) }
        }
        book.value?.let { bookRepository.updateBook(it) }
    }

    suspend fun removeBook(book: Book) {
        bookRepository.removeBook(book)
    }

    override fun onCleared() {
        super.onCleared()
        book.value?.let { bookRepository.updateBook(it) }
    }
}