package com.example.filmbookdiary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.filmbookdiary.data.Film

@Database(entities = [ Film::class ], version = 2)
@TypeConverters(FilmTypeConverters::class)
abstract class FilmDatabase: RoomDatabase() {
    abstract fun filmDao(): FilmDao
}

val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE films ADD COLUMN rating INTEGER")
    }
}