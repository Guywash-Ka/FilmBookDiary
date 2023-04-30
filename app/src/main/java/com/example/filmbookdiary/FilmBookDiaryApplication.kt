package com.example.filmbookdiary

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FilmBookDiaryApplication: Application() {
    companion object {
        const val maxRating = 10
    }
}