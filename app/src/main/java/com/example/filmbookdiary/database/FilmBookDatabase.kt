package com.example.filmbookdiary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.filmbookdiary.data.Book
import com.example.filmbookdiary.data.Film

@Database(entities = [ Film::class, Book::class ], version = 7)
@TypeConverters(FBTypeConverters::class)
abstract class FilmBookDatabase: RoomDatabase() {
    abstract fun filmDao(): FilmDao
    abstract fun bookDao(): BookDao
}

val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE films ADD COLUMN rating INTEGER")
    }
}

val migration_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `books` (`id` BLOB NOT NULL, `imageUri` TEXT NOT NULL, `name` TEXT NOT NULL, `author` TEXT NOT NULL, `description` TEXT NOT NULL, `rating` INTEGER, PRIMARY KEY(`id`))")
    }
}

val migration_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE films ADD COLUMN isWatched INTEGER NOT NULL DEFAULT 1")
        database.execSQL("ALTER TABLE books ADD COLUMN isRead INTEGER NOT NULL DEFAULT 1")
    }
}

val migration_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE films ADD COLUMN author TEXT")
    }
}

val migration_5_6 = object : Migration(5, 6) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE films ADD COLUMN emoji TEXT NOT NULL DEFAULT '\uD83C\uDF9E'")
    }
}

val migration_6_7 = object : Migration(6, 7) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE books ADD COLUMN emoji TEXT NOT NULL DEFAULT '\uD83D\uDCD6'")
    }
}