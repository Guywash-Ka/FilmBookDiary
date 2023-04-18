package com.example.filmbookdiary.ui.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.filmbookdiary.data.Film
import com.example.filmbookdiary.viewmodel.FilmViewModel
import com.skydoves.landscapist.glide.GlideImage
import java.util.UUID

@Composable
fun FilmPage(
    modifier: Modifier = Modifier,
    filmID: UUID,
    filmImageUri: Uri,
    filmName: String,
    filmDescription: String,
    onFilmClicked: (UUID) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp),
        elevation = 10.dp,
        modifier = modifier.clickable { onFilmClicked(filmID) }
    ) {
        Column(modifier = modifier.background(color = colors.primary)) {
            Box(contentAlignment = Alignment.TopEnd) {
                GlideImage(
                    imageModel = { filmImageUri },
                    modifier = modifier.height(280.dp)
                )
                IconButton(onClick = { /* TODO */ }) {
                    Icon(
                        Icons.Filled.Clear,
                        contentDescription = "Remove element",
                        tint = colors.onPrimary
                    )
                }
            }
            Column() {
                Text(
                    modifier = modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.ExtraBold,
                    text = filmName,
                    style = typography.h4,
                    color = colors.onPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = modifier.padding(start = 16.dp, bottom = 8.dp),
                    text = filmDescription,
                    color = colors.onPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun FilmList(
    modifier: Modifier,
    films: List<Film>,
    onFilmClicked: (UUID) -> Unit,
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp), contentPadding = PaddingValues(all = 8.dp)) {
        items(
            items = films,
            itemContent = {
                FilmPage(
                    filmImageUri = it.imageUri,
                    filmName = it.name,
                    filmDescription = it.description,
                    onFilmClicked = onFilmClicked,
                    filmID = it.id,
                )
            },
        )
    }
}