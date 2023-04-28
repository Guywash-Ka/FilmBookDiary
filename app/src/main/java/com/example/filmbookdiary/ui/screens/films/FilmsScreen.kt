package com.example.filmbookdiary.ui.screens.films

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.filmbookdiary.R
import com.example.filmbookdiary.data.Film
import com.example.filmbookdiary.data.FilterState
import com.example.filmbookdiary.ui.components.FilmList
import com.example.filmbookdiary.viewmodel.FilmViewModel
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun FilmsScreen(
    modifier: Modifier = Modifier,
    filmViewModel: FilmViewModel = viewModel(),
    navigateToSingleFilm: (UUID) -> Unit = {},
    searchTextState: String,
    filterSelectState: FilterState
) {


//    val filmsList = filmViewModel.films.collectAsState(emptyList()).value
    val preFilmsList = if (searchTextState == "") {
        filmViewModel.films.collectAsState(emptyList()).value.reversed()
    } else {
        filmViewModel.searchFilmsByName(searchTextState).collectAsState(emptyList()).value.reversed()
    }
    val filmsList = when (filterSelectState) { // TODO СДЕЛАТЬ ЧЕРЕЗ БД
        FilterState.DATE -> preFilmsList
        FilterState.NAME -> preFilmsList.sortedBy { it.name }
        FilterState.RATING -> preFilmsList.sortedBy { it.rating }.reversed()
    }
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = modifier.fillMaxSize(1f)) {
        FilmList(
            modifier = modifier,
            films = filmsList,
            onFilmClicked = navigateToSingleFilm,
//            filmViewModel = filmViewModel
        )
        FloatingActionButton(
            onClick = {
                var newUUID: UUID
                coroutineScope.launch {
                    newUUID = UUID.randomUUID()
                    filmViewModel.addFilm(Film(newUUID, Uri.parse("android.resource://com.example.filmbookdiary/" + R.drawable.empty_photo), "New Film", "Very cool film", null, true, "Author name", "\uD83E\uDD20"))
                    navigateToSingleFilm(newUUID)
                }
            },
            modifier = modifier
                .padding(4.dp)
                .align(alignment = Alignment.BottomEnd),
            elevation = FloatingActionButtonDefaults.elevation(6.dp),
        ) {
            Icon(Icons.Filled.Add, "Add new element")
        }
    }
}