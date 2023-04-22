package com.example.filmbookdiary.data

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "films")
data class Film(
    @PrimaryKey val id: UUID,
    val imageUri: Uri,
    val name: String,
    val description: String,
    val rating: Int?,
    val isWatched: Boolean
)

