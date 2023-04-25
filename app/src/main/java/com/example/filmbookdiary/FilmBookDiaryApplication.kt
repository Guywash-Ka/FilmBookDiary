package com.example.filmbookdiary

import android.app.Application
import com.example.filmbookdiary.nav.repositories.BookRepository
import com.example.filmbookdiary.nav.repositories.FilmRepository

class FilmBookDiaryApplication: Application() {
    companion object {
        const val maxRating = 10
    }

    override fun onCreate() {
        super.onCreate()
        FilmRepository.initialize(this)
        BookRepository.initialize(this)
    }
}