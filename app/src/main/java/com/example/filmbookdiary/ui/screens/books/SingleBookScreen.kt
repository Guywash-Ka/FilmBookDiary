package com.example.filmbookdiary.ui.screens.books

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun SingleBookScreen(
    bookID: String?
) {
    Text(bookID.toString())
}