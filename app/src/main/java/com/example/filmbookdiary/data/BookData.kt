package com.example.filmbookdiary.data

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "books")
data class Book(
    @PrimaryKey val id: UUID,
    val imageUri: Uri,
    val name: String,
    val author: String,
    val description: String,
    val rating: Int?
)