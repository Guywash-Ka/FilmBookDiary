package com.example.filmbookdiary.database

import androidx.room.*
import com.example.filmbookdiary.data.Book
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface BookDao {

    @Query("SELECT * FROM books")
    fun getBooks(): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE id=(:id)")
    suspend fun getBook(id: UUID): Book

    @Insert
    suspend fun addBook(book: Book)

    @Delete
    suspend fun removeBook(book: Book)

    @Query("DELETE FROM books")
    suspend fun removeAllBooks()

    @Update
    suspend fun updateBook(book: Book)

    @Query("SELECT * FROM books WHERE name LIKE '%' || :bookName || '%'")
    fun searchBooksByName(bookName: String): Flow<List<Book>>

    @Query("SELECT COUNT(id) FROM books WHERE isRead = 1")
    fun getNumberOfReadBooks(): Flow<Int>

    @Query("SELECT COUNT(id) FROM books WHERE isRead = 0")
    fun getNumberOfNotReadBooks(): Flow<Int>
}