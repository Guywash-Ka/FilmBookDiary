package com.example.filmbookdiary.ui.components

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.filmbookdiary.data.Film
import java.util.UUID

@Composable
fun FilmPage(
    modifier: Modifier = Modifier,
    filmID: UUID,
    filmImageUri: Uri,
    filmName: String,
    filmDescription: String,
    filmRating: Int?,
    filmAuthor: String?,
    filmEmoji: String,
    onFilmClicked: (UUID) -> Unit,
) {
    FilmCard(
        modifier = modifier,
        filmID = filmID,
        filmImageUri = filmImageUri,
        filmName = filmName,
        filmDescription = filmDescription,
        filmRating = filmRating,
        filmAuthor = filmAuthor,
        filmEmoji = filmEmoji,
        onFilmClicked = onFilmClicked
    )
}

@Composable
fun FilmList(
    modifier: Modifier,
    films: List<Film>,
    onFilmClicked: (UUID) -> Unit
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp), contentPadding = PaddingValues(all = 10.dp)) {
        items(
            items = films,
            itemContent = {
                FilmPage(
                    filmID = it.id,
                    filmImageUri = it.imageUri,
                    filmName = it.name,
                    filmDescription = it.description,
                    filmRating = it.rating,
                    filmAuthor = it.author,
                    filmEmoji = it.emoji,
                    onFilmClicked = onFilmClicked,
                )
            }
        )
    }
}