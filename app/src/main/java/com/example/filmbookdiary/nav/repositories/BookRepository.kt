package com.example.filmbookdiary.nav.repositories

import android.content.Context
import androidx.room.Room
import com.example.filmbookdiary.data.Book
import com.example.filmbookdiary.data.Film
import com.example.filmbookdiary.database.FilmBookDatabase
import com.example.filmbookdiary.database.migration_1_2
import com.example.filmbookdiary.database.migration_2_3
import com.example.filmbookdiary.database.migration_3_4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID

private const val DATABASE_NAME = "FBDiary_db"

class BookRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
){
    private val database: FilmBookDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            FilmBookDatabase::class.java,
            DATABASE_NAME
        )
        .addMigrations(migration_1_2)
        .addMigrations(migration_2_3)
        .addMigrations(migration_3_4)
        .build()

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

    companion object {
        private var INSTANCE: BookRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = BookRepository(context)
            }
        }

        fun get(): BookRepository {
            return INSTANCE ?: throw java.lang.IllegalStateException("BookRepository must be initialized")
        }
    }
}