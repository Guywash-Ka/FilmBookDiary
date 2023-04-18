package com.example.filmbookdiary.ui.screens.films

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.filmbookdiary.viewmodel.SingFilmViewModelFactory
import com.example.filmbookdiary.viewmodel.SingleFilmViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import java.util.*

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SingleFilmScreen(
    filmID: String? = UUID.randomUUID().toString(), // TODO(сейчас реализовано через filmType который возвращает название фильма, надо переделать под UUID)
    modifier: Modifier = Modifier,
    singleFilmViewModel: SingleFilmViewModel = viewModel(factory = SingFilmViewModelFactory(UUID.fromString(filmID))),
) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val film = singleFilmViewModel.film.collectAsState(null).value

    val filmName = film?.name
    val filmImageUri = film?.imageUri
    val filmDescription = film?.description

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        singleFilmViewModel.updateFilm { oldFilm ->
            oldFilm.copy(imageUri = imageUri!!)
        }
    }

    Column(modifier = modifier.verticalScroll(enabled = true, state = ScrollState(0))) {
        GlideImage(
            imageModel = { imageUri ?: filmImageUri },
            modifier = modifier.height(420.dp),
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop
            )
        )
        if (filmName != null) {
            Text(
                modifier = modifier.padding(start = 8.dp),
                fontWeight = FontWeight.ExtraBold,
                text = filmName, //TODO
                style = MaterialTheme.typography.h4,
            )
        }
        if (filmDescription != null) {
            Text(
                modifier = modifier.padding(start = 16.dp, bottom = 8.dp),
                text = filmDescription, //TODO
            )
        }
        Button(onClick = { launcher.launch("image/*") }) {
            Text(text = "Change image")
        }
    }
}