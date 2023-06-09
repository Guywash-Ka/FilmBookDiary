package com.example.filmbookdiary.database

import android.net.Uri
import androidx.room.TypeConverter

class FBTypeConverters {
    @TypeConverter
    fun fromUri(uri: Uri): String {
        return uri.toString()
    }

    @TypeConverter
    fun toUri(uriString: String): Uri {
        return Uri.parse(uriString)
    }
}