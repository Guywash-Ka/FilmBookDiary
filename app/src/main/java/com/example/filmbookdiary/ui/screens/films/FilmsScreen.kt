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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.filmbookdiary.ui.components.FilmList
import com.example.filmbookdiary.viewmodel.FilmViewModel

@Composable
fun FilmsScreen(
    modifier: Modifier = Modifier,
    filmViewModel: FilmViewModel = viewModel(),
    onFilmClicked: (String) -> Unit = {},
) {
    val filmsList = filmViewModel.films.collectAsState(emptyList()).value
    Box {
        FilmList(
            modifier = modifier,
            films = filmsList,
            onFilmClicked = onFilmClicked
        )
        FloatingActionButton(
            onClick = { /*TODO*/ },
            modifier = modifier
                .padding(4.dp)
                .align(alignment = Alignment.BottomEnd),
            elevation = FloatingActionButtonDefaults.elevation(4.dp),
            backgroundColor = colors.primaryVariant
        ) {
            Icon(Icons.Filled.Add, "Add new element")
        }
    }
}