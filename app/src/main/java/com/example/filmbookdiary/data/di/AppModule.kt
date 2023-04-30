package com.example.filmbookdiary.data.di

import android.content.Context
import androidx.room.Room
import com.example.filmbookdiary.database.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "FBDiary_db"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): FilmBookDatabase {
        return Room.databaseBuilder(
            appContext,
            FilmBookDatabase::class.java,
            DATABASE_NAME
        )
            .addMigrations(migration_1_2)
            .addMigrations(migration_2_3)
            .addMigrations(migration_3_4)
            .addMigrations(migration_4_5)
            .addMigrations(migration_5_6)
            .addMigrations(migration_6_7)
            .build()
    }
}