package com.example.filmbookdiary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmbookdiary.data.Book
import com.example.filmbookdiary.repositories.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class BookViewModel: ViewModel() {

    private val bookRepository = BookRepository.get()

    private val _books: MutableStateFlow<List<Book>> = MutableStateFlow(emptyList())
    val books: StateFlow<List<Book>>
        get() = _books.asStateFlow()

    suspend fun getBook(id: UUID): Book {
        return bookRepository.getBook(id)
    }

    suspend fun addBook(book: Book) {
        bookRepository.addBook(book)
    }

    suspend fun removeBook(book: Book) {
        bookRepository.removeBook(book)
    }

    suspend fun removeAllBooks() {
        bookRepository.removeAllBooks()
    }

    fun searchBooksByName(name: String): Flow<List<Book>> {
        return bookRepository.searchBooksByName(name)
    }

    init {
        viewModelScope.launch {
            bookRepository.getBooks().collect {
                _books.value = it
            }
        }
    }
}