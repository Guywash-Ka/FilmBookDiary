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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.filmbookdiary.data.Film
import com.example.filmbookdiary.viewmodel.FilmViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch
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
//    filmViewModel: FilmViewModel
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
//    Card(
//        shape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp),
//        elevation = 10.dp,
//        modifier = modifier.clickable { onFilmClicked(filmID) }
//    ) {
//        Column(modifier = modifier.background(color = colors.primary)) {
//            Box(contentAlignment = Alignment.TopEnd) {
//                GlideImage(
//                    imageModel = { filmImageUri },
//                    modifier = modifier.height(280.dp)
//                )
////                IconButton(onClick = {
////                    coroutineScope.launch {
////                        filmViewModel.removeFilm(filmViewModel.getFilm(filmID))
////                    }
////                }) {
////                    Icon(
////                        Icons.Filled.Clear,
////                        contentDescription = "Remove element",
////                        tint = colors.onPrimary
////                    )
////                }
//            }
//            Row(
//                modifier = modifier.fillMaxWidth(1f),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Column() {
//                    Text(
//                        modifier = modifier.padding(start = 8.dp),
//                        fontWeight = FontWeight.ExtraBold,
//                        text = filmName,
//                        style = typography.h4,
//                        color = colors.onPrimary,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                    Text(
//                        modifier = modifier.padding(start = 16.dp, bottom = 8.dp),
//                        text = filmDescription,
//                        color = colors.onPrimary,
//                        maxLines = 2,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                }
//                if (filmRating != null) {
//                    Text(
//                        text = "$filmRating/10",
//                        fontWeight = FontWeight.ExtraBold,
//                        style = typography.h4,
//                        color = colors.onPrimary
//                    )
//                }
//            }
//        }
//    }
}

@Composable
fun FilmList(
    modifier: Modifier,
    films: List<Film>,
    onFilmClicked: (UUID) -> Unit,
//    filmViewModel: FilmViewModel
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
//                    filmViewModel = filmViewModel
                )
            },
        )
    }
}