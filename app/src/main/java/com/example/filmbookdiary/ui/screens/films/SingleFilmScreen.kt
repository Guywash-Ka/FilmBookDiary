package com.example.filmbookdiary.ui.screens.films

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.example.filmbookdiary.data.FilmData
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.first

@Composable
fun SingleFilmScreen(
    filmType: String? = FilmData.getFilmsNotFlow().first().name, //TODO(возвращает просто список, а не флоу, так делать не стоит)
    modifier: Modifier = Modifier
) {
    val film = remember(filmType) { //TODO(Хранит целый экземпляр фильма, можно ли оставить только нужные поля?)
        FilmData.getFilm(filmType)
    }

    Column {
//        Image(
//            modifier = modifier.fillMaxWidth(1f),
//            painter = painterResource(film.imageId),
//            contentDescription = "Film Photo",
//            contentScale = ContentScale.FillWidth
//        )
        GlideImage(
            imageModel = { film.imageUri },
            // Crop, Fit, Inside, FillHeight, FillWidth, None
            imageOptions = ImageOptions(
                contentScale = ContentScale.FillWidth
            )
        )
        Text(
            modifier = modifier.padding(start = 8.dp),
            fontWeight = FontWeight.ExtraBold,
            text = film.name,
            style = MaterialTheme.typography.h4,
        )
        Text(
            modifier = modifier.padding(start = 16.dp, bottom = 8.dp),
            text = film.description,
        )
    }


}