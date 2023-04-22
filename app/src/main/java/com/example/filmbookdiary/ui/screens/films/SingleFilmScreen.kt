package com.example.filmbookdiary.ui.screens.films

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.filmbookdiary.ui.components.RatingSelection
import com.example.filmbookdiary.viewmodel.SingFilmViewModelFactory
import com.example.filmbookdiary.viewmodel.SingleFilmViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SingleFilmScreen(
    modifier: Modifier = Modifier,
    filmID: String? = UUID.randomUUID().toString(),
    singleFilmViewModel: SingleFilmViewModel = viewModel(factory = SingFilmViewModelFactory(UUID.fromString(filmID))),
) {
    val isEdited = remember { mutableStateOf(false) }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val film = singleFilmViewModel.film.collectAsState(null).value

    val filmName = film?.name
    val filmImageUri = film?.imageUri
    val filmDescription = film?.description
    val filmRating = film?.rating
    val filmIsWatched = film?.isWatched

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        singleFilmViewModel.updateFilm { oldFilm ->
            oldFilm.copy(imageUri = imageUri!!)
        }
    }

    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()

    fun updateFilm(rating: Int) {
        singleFilmViewModel.updateFilm { oldFilm ->
            oldFilm.copy(rating = rating)
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            RatingSelection(sheetState, ::updateFilm)
        },
        sheetBackgroundColor = colors.surface,
    ) {
        Column(modifier = modifier.verticalScroll(enabled = true, state = ScrollState(0))) {
            Box(contentAlignment = Alignment.BottomEnd) {
                GlideImage(
                    imageModel = { imageUri ?: filmImageUri },
                    modifier = modifier.height(420.dp),
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop
                    )
                )
                if (isEdited.value) {
                    IconButton(onClick = { launcher.launch("image/*") }) {
                        Icon(Icons.Filled.Edit, contentDescription = "Change image")
                    }
                }
            }
            if (isEdited.value) {
                EditFilmScreen(
                    filmName = filmName,
                    filmDescription = filmDescription,
                    filmRating = filmRating,
                    singleFilmViewModel = singleFilmViewModel
                )
            } else {
                ShowFilmScreen(
                    modifier = modifier,
                    filmName = filmName,
                    filmRating = filmRating,
                    filmDescription = filmDescription,
                    filmIsWatched = filmIsWatched,
                )
            }

            Button(onClick = {
                isEdited.value = !isEdited.value
            }) {
                Text(if (isEdited.value) "Save" else "Edit")
            }

            IconButton(
                onClick = {
                    scope.launch {
                        if (!sheetState.isVisible) {
                            sheetState.show()
                        } else {
                            sheetState.hide()
                        }
                    }
                    },

            ) {
                Icon(Icons.Filled.Star, contentDescription = "Rate", tint = Color.Yellow)
            }
        }
    }
}

@Composable
fun EditFilmScreen(
    filmName: String?,
    filmDescription: String?,
    filmRating: Int?,
    singleFilmViewModel: SingleFilmViewModel
) {
    var name by remember { mutableStateOf(filmName) }
    var description by remember { mutableStateOf(filmDescription) }
    var rating by remember { mutableStateOf(filmRating) }

    name?.let {
        TextField(
            value = it,
            onValueChange = { newNameText ->
                if (!newNameText.contains("\n")) {
                    name = newNameText
                    singleFilmViewModel.updateFilm { oldFilm ->
                        oldFilm.copy(name = name!!)
                    }
                } },
            textStyle = MaterialTheme.typography.h4,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            )
        ) }
    description?.let {
        OutlinedTextField(
            value = it,
            onValueChange = { newDescriptionText ->
                description = newDescriptionText
                singleFilmViewModel.updateFilm { oldFilm ->
                    oldFilm.copy(description = description!!)
                }
            },
            label = { Text("Description") }
//            colors = TextFieldDefaults.textFieldColors(
//                backgroundColor = Color.Transparent,
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent,
//                disabledIndicatorColor = Color.Transparent,
//                errorIndicatorColor = Color.Transparent
//            )
        ) }
}

@Composable
fun ShowFilmScreen(
    modifier: Modifier,
    filmName: String?,
    filmRating: Int?,
    filmDescription: String?,
    filmIsWatched: Boolean?,
) {
    filmName?.let {
        Text(
        modifier = modifier.padding(start = 8.dp),
        fontWeight = FontWeight.ExtraBold,
        text = filmName,
        style = MaterialTheme.typography.h4,
    ) }

    filmRating?.let {
        Text(
            modifier = modifier.padding(start = 8.dp),
            fontWeight = FontWeight.ExtraBold,
            text = "$filmRating/10",
            style = MaterialTheme.typography.h4,
        )
    }

    filmDescription?.let {
        Text(
            modifier = modifier.padding(start = 16.dp, bottom = 8.dp),
            text = filmDescription,
        )
    }

    filmIsWatched?.let {
        Text(text = if (filmIsWatched) {
            "Просмотрено!"
        } else {
            "Надо посмотреть!"
        },
            style = MaterialTheme.typography.h4
        )
    }
}