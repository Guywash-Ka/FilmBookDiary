package com.example.filmbookdiary.ui.screens.films

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.filmbookdiary.R
import com.example.filmbookdiary.data.Film
import com.example.filmbookdiary.ui.components.FilmList
import com.example.filmbookdiary.viewmodel.FilmViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

@Composable
fun FilmsScreen(
    modifier: Modifier = Modifier,
    filmViewModel: FilmViewModel = viewModel(),
    onFilmClicked: (UUID) -> Unit = {},
) {
    val filmsList = filmViewModel.films.collectAsState(emptyList()).value
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxSize(1f)) {
        FilmList(
            modifier = modifier,
            films = filmsList,
            onFilmClicked = onFilmClicked
        )
        FloatingActionButton(
            onClick = { coroutineScope.launch {
                    filmViewModel.addFilm(Film(UUID.randomUUID(), Uri.parse("android.resource://com.example.filmbookdiary/" + R.drawable.drive_photo), "Drive", "Very cool film"))
                }
            },
            modifier = modifier
                .padding(4.dp)
                .align(alignment = Alignment.BottomEnd),
            elevation = FloatingActionButtonDefaults.elevation(4.dp),
            backgroundColor = colors.primaryVariant
        ) {
            Icon(Icons.Filled.Add, "Add new element")
        }

        FloatingActionButton(
            onClick = { coroutineScope.launch {
                filmViewModel.removeAllFilms()
            }
            },
            modifier = modifier
                .padding(4.dp)
                .align(alignment = Alignment.BottomStart),
            elevation = FloatingActionButtonDefaults.elevation(4.dp),
            backgroundColor = colors.primaryVariant
        ) {
            Icon(Icons.Filled.Clear, "Remove all elements")
        }
    }
}