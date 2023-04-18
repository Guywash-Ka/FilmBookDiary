package com.example.filmbookdiary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.filmbookdiary.data.Film

@Database(entities = [ Film::class ], version = 1)
@TypeConverters(FilmTypeConverters::class)
abstract class FilmDatabase: RoomDatabase() {
    abstract fun filmDao(): FilmDao
}