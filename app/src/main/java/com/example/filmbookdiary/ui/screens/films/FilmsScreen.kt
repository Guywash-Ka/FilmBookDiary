package com.example.filmbookdiary.ui.screens.films

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.filmbookdiary.ui.components.FilmList
import com.example.filmbookdiary.viewmodel.FilmViewModel

@Composable
fun FilmsScreen(
    modifier: Modifier = Modifier,
    filmViewModel: FilmViewModel = viewModel(),
    onFilmClicked: (String) -> Unit = {},
) {
    FilmList(
        modifier = modifier,
        films = filmViewModel.films,
        onFilmClicked = onFilmClicked
    )
}