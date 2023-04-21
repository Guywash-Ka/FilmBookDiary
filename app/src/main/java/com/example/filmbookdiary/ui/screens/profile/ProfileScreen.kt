package com.example.filmbookdiary.ui.screens.profile

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.filmbookdiary.viewmodel.FilmViewModel
import com.example.filmbookdiary.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = viewModel(),
) {
    val filmsList = profileViewModel.films.collectAsState().value
    val booksList = profileViewModel.books.collectAsState().value

    val filmsNumber = filmsList.size
    val booksNumber = booksList.size
    Column() {
        Text(text = "You've watched $filmsNumber films!", modifier = modifier, fontWeight = FontWeight.ExtraBold, style = MaterialTheme.typography.h1)
        Text(text = "You've read $booksNumber books!", modifier = modifier, style = MaterialTheme.typography.h1, fontWeight = FontWeight.ExtraBold)
    }
}