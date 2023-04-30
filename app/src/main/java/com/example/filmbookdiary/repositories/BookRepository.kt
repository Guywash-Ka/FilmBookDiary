package com.example.filmbookdiary.repositories

import com.example.filmbookdiary.data.Book
import com.example.filmbookdiary.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class BookRepository @Inject constructor(
    val database: FilmBookDatabase
){
    @OptIn(DelicateCoroutinesApi::class)
    private val coroutineScope: CoroutineScope = GlobalScope

    fun getBooks(): Flow<List<Book>> = database.bookDao().getBooks()

    suspend fun getBook(id: UUID): Book = database.bookDao().getBook(id)

    suspend fun addBook(book: Book) = database.bookDao().addBook(book)

    suspend fun removeBook(book: Book) = database.bookDao().removeBook(book)

    suspend fun removeAllBooks() = database.bookDao().removeAllBooks()

    fun updateBook(book: Book) {
        coroutineScope.launch {
            database.bookDao().updateBook(book)
        }
    }

    fun searchBooksByName(name: String): Flow<List<Book>> = database.bookDao().searchBooksByName(name)

    fun getNumberOfReadBooks() = database.bookDao().getNumberOfReadBooks()
    fun getNumberOfNotReadBooks() = database.bookDao().getNumberOfNotReadBooks()
}