package com.example.filmbookdiary.ui.screens.films

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
import com.example.filmbookdiary.R
import com.example.filmbookdiary.data.Film
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import java.util.*

@Composable
fun SingleFilmScreen(
    filmType: String? = "",
    modifier: Modifier = Modifier,
) {
    val film = remember(filmType) {
        Film(UUID(123, 2), Uri.parse("android.resource://com.example.filmbookdiary/" + R.drawable.drive_photo), "Drive", "Very cool film")
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    Column(modifier = modifier.verticalScroll(enabled = true, state = ScrollState(0))) {
        GlideImage(
            imageModel = { imageUri ?: film.imageUri},
            modifier = modifier.height(420.dp),
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop
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
        Button(onClick = { launcher.launch("image/*") }) {
            Text(text = "Change image")
        }

        imageUri?.let {
            film.imageUri = imageUri as Uri
        }
    }
}