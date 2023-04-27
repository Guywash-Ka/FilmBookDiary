package com.example.filmbookdiary.ui.screens.films

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.createChooser
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.filmbookdiary.data.WidgetState
import com.example.filmbookdiary.ui.components.RatingSelection
import com.example.filmbookdiary.ui.components.SingleFilmPicture
import com.example.filmbookdiary.ui.theme.*
import com.example.filmbookdiary.viewmodel.SingFilmViewModelFactory
import com.example.filmbookdiary.viewmodel.SingleFilmViewModel
import kotlinx.coroutines.launch
import java.util.*
import kotlin.reflect.KFunction1

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SingleFilmScreen(
    modifier: Modifier = Modifier,
    filmID: String? = UUID.randomUUID().toString(),
    navigateBack: () -> Unit,
    singleFilmViewModel: SingleFilmViewModel = viewModel(factory = SingFilmViewModelFactory(UUID.fromString(filmID))),
) {
    val isEdited = remember { mutableStateOf(false) }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val scope = rememberCoroutineScope()

    val film = singleFilmViewModel.film.collectAsState(null).value

    val filmName = film?.name
    val filmImageUri = film?.imageUri
    val filmDescription = film?.description
    val filmRating = film?.rating
    val filmIsWatched = film?.isWatched
    val filmAuthor = film?.author
    val filmEmoji = film?.emoji

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        imageUri?.let {
            singleFilmViewModel.updateFilm { oldFilm ->
                oldFilm.copy(imageUri = imageUri!!)
            }
        }
    }

    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden
    )

    fun updateFilmRating(rating: Int) {
        singleFilmViewModel.updateFilm { oldFilm ->
            oldFilm.copy(rating = rating)
        }
    }

    fun updateFilmIsWatched(isWatched: Boolean) {
        singleFilmViewModel.updateFilm { oldFilm ->
            oldFilm.copy(isWatched = isWatched)
        }
    }

    fun updateFilmEmoji(emoji: String) {
        singleFilmViewModel.updateFilm { oldFilm ->
            oldFilm.copy(emoji = emoji)
        }
    }
    fun updateFilmName(name: String) {
        singleFilmViewModel.updateFilm { oldFilm ->
            oldFilm.copy(name = name)
        }
    }
    fun updateFilmDescription(description: String) {
        singleFilmViewModel.updateFilm { oldFilm ->
            oldFilm.copy(description = description)
        }
    }
    fun updateFilmAuthor(author: String) {
        singleFilmViewModel.updateFilm { oldFilm ->
            oldFilm.copy(author = author)
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            RatingSelection(sheetState, ::updateFilmRating)
        },
        sheetContentColor = colors.background,
        sheetBackgroundColor = colors.surface,
    ) {
        Column(modifier = modifier
            .verticalScroll(enabled = true, state = ScrollState(0))
            .background(color = Color.White)
            .fillMaxHeight(1f)) {
            Box(contentAlignment = Alignment.Center) {
                SingleFilmPicture(
                    filmImageUri = filmImageUri,
                    imageUri = imageUri,
                    modifier = modifier
                )
                if (isEdited.value) {
                    FloatingActionButton(
                        onClick = { launcher.launch("image/*") },
                        modifier = modifier.align(Alignment.BottomEnd).padding(6.dp).size(26.dp))
                    {
                        Icon(Icons.Filled.Edit, contentDescription = "Change image", Modifier.size(20.dp))
                    }
                    FloatingActionButton(
                        onClick = {
                            scope.launch {
                                if (film != null) {
                                    singleFilmViewModel.removeFilm(film)
                                    navigateBack()
                                }
                            }
                                  },
                        modifier = modifier.align(Alignment.TopEnd).padding(6.dp).size(26.dp))
                    {
                        Icon(Icons.Filled.Clear, contentDescription = "Delete Film", Modifier.size(20.dp))
                    }
                }
            }
            if (isEdited.value) {
                EditFilmScreen(
                    filmName = filmName,
                    filmAuthor = filmAuthor,
                    filmDescription = filmDescription,
                    filmRating = filmRating,
                    filmEmoji = filmEmoji,
                    isEdited = isEdited,
                    updateName = ::updateFilmName,
                    updateDescription = ::updateFilmDescription,
                    updateAuthor = ::updateFilmAuthor,
                )
            } else {
                ShowFilmScreen(
                    modifier = modifier,
                    filmName = filmName,
                    filmRating = filmRating,
                    filmDescription = filmDescription,
                    filmAuthor = filmAuthor,
                    filmIsWatched = filmIsWatched,
                    filmEmoji = filmEmoji,
                    sheetState = sheetState,
                    isEdited = isEdited,
                    updateFilm = ::updateFilmIsWatched,
                    updateEmoji = ::updateFilmEmoji
                )
            }
        }
    }
}

@Composable
fun EditFilmScreen(
    filmName: String?,
    filmAuthor: String?,
    filmDescription: String?,
    filmRating: Int?,
    filmEmoji: String?,
    isEdited: MutableState<Boolean>,
    updateName: KFunction1<String, Unit>,
    updateDescription: KFunction1<String, Unit>,
    updateAuthor: KFunction1<String, Unit>,
) {
    var name by remember { mutableStateOf(filmName) }
    var author by remember { mutableStateOf(filmAuthor) }
    var description by remember { mutableStateOf(filmDescription) }
    var rating by remember { mutableStateOf(filmRating) }
    Column(modifier = Modifier.fillMaxWidth(1f), horizontalAlignment = Alignment.CenterHorizontally) {
        name?.let {
            TextField(
                value = it,
                onValueChange = { newNameText ->
                    if (!newNameText.contains("\n")) {
                        name = newNameText
                        updateName(name ?: " ")
                    } },
                textStyle = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp,
                    color = textColor
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            ) }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(1f),
            value = if (author != null) { author!! } else { " " },
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Author") },
            onValueChange = { newAuthor ->
                author = newAuthor
                updateAuthor(author ?: " ")
            },
            singleLine = true,
            label = { Text("Режиссер") }
        )

        description?.let {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(1f),
                value = it,
                leadingIcon = { Icon(imageVector = Icons.Default.Info, contentDescription = "info") },
                onValueChange = { newDescriptionText ->
                    description = newDescriptionText
                    updateDescription(description ?: "")
                },
                label = { Text("Описание") }
            )
        }
    }

    IconButton(onClick = {
        isEdited.value = !isEdited.value
    }) {
        Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Back")
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowFilmScreen(
    modifier: Modifier,
    filmName: String?,
    filmRating: Int?,
    filmDescription: String?,
    filmAuthor: String?,
    filmIsWatched: Boolean?,
    filmEmoji: String?,
    sheetState: ModalBottomSheetState,
    isEdited: MutableState<Boolean>,
    updateFilm: KFunction1<Boolean, Unit>,
    updateEmoji: KFunction1<String, Unit>,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val emojiRowState = remember { mutableStateOf(WidgetState.CLOSED) }
    val emojiList = listOf("😎", "😂", "😡", "🤠", "😭", "❤", "😴", "😱", "💔", "🔫", "🧟", "🤡")
    Card(shape = RoundedCornerShape(10.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            filmName?.let {
                Text(
                    modifier = modifier.padding(start = 8.dp, bottom = 16.dp),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp,
                    text = filmName,
                    color = textColor
                ) }
            filmAuthor?.let {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Author Icon",
                        tint = secondaryTextColor
                    )
                    Text(
                        modifier = modifier.padding(start = 8.dp),
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                        text = filmAuthor,
                        color = secondaryTextColor,
                    )
                }
            }
            Row(
                modifier = modifier
                    .fillMaxWidth(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = {
                    scope.launch {
                        if (filmIsWatched == null || !filmIsWatched) {
                            updateFilm(true)
                        } else {
                            updateFilm(false)
                        }
                    }
                    Toast.makeText(
                        context,
                        if (filmIsWatched == null || !filmIsWatched) {
                            "Фильм просмотрен!"
                        } else {
                            "Надо посмотреть!"
                        },
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                    if (filmIsWatched == true){
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Icon check",
                            tint = Color.Green
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Icon check",
                            tint = Color.Red
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .padding(0.dp),
                ) {
                    Row() {
                        filmRating?.let {
                            Text(
                                modifier = modifier.fillMaxHeight(1f),
//                        textAlign = TextAlign.Justify,
                                fontWeight = FontWeight.ExtraBold,
                                text = "$filmRating",
                                fontSize = 20.sp,
                                color = when(filmRating){
                                    in 1..4 -> Color(0xFFFF80CE)
                                    in 5..7 -> Color(0xFFFEC404)
                                    else -> Color(0xFF71C98D)
                                }
                            )
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
                            modifier = modifier,
                        )
                        {
                            Icon(
                                modifier = modifier,
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Star Icon",
                                tint = if (filmRating != null) {
                                    when(filmRating) {
                                        in 1..4 -> Color(0xFFFF80CE)
                                        in 5..7 -> Color(0xFFFEC404)
                                        else -> Color(0xFF71C98D)
                                    }
                                } else {
                                    Color.Gray
                                }
                            )
                        }
                    }
                }
                TextButton(
                    onClick = { if (emojiRowState.value == WidgetState.CLOSED) {
                        emojiRowState.value = WidgetState.OPENED
                    } else {
                        emojiRowState.value = WidgetState.CLOSED
                    }
                    }
                ) {
                    Text(text = filmEmoji ?: "\uD83D\uDE34", fontSize = 20.sp, modifier = modifier)
                }
            }

            if (emojiRowState.value == WidgetState.OPENED) {
                LazyRow() {
                    items(emojiList) {emoji ->
                        TextButton(
                            onClick = {
                                emojiRowState.value = WidgetState.CLOSED
                                updateEmoji(emoji)
                            },
                            modifier = modifier.padding(5.dp)
                        ) {
                            Text(text = emoji, fontSize = 24.sp)
                        }
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                IconButton(onClick = {
                    shareFilm(context, filmName ?: " ", filmRating)
                }) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = Color.Gray)
                }

                IconButton(onClick = {
                    isEdited.value = !isEdited.value
                }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Icon", tint = Color.Gray)
                }
            }

            Canvas(modifier = Modifier
                .fillMaxWidth(1f)
                .padding(vertical = 20.dp)) {

                val canvasWidth = size.width
                val canvasHeight = size.height

                drawLine(
                    start = Offset(x = 60f, y = 0f),
                    end = Offset(x = canvasWidth - 60, y = 0f),
                    color = backgroundColor,
                    strokeWidth = 4F
                )
            }

            filmDescription?.let {
                Text(
                    modifier = modifier
                        .padding(start = 16.dp, bottom = 8.dp)
                        .fillMaxHeight(1f),
                    text = filmDescription,
                    color = textColor,
                )
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun ShowFilmScreenPreview() {
    fun someFun(boolArg: Boolean) {
    }
    fun someFun2(stringArg: String) {
    }

    ShowFilmScreen(
        modifier = Modifier,
        filmName = "Film name",
        filmRating = 6,
        filmDescription = "Very cool film",
        filmAuthor = "Film Author",
        filmIsWatched = false,
        filmEmoji = "\uD83D\uDE0E",
        sheetState = ModalBottomSheetState(ModalBottomSheetValue.Hidden),
        isEdited = mutableStateOf(false),
        updateFilm = ::someFun,
        updateEmoji = ::someFun2,
    )
}

fun shareFilm(context: Context, filmName: String, filmRating: Int?) {
    val message = """
                        Очень рекомендую посмотреть фильм "$filmName"!
                        
                        ${if (filmRating != null) "Я оценил его на $filmRating баллов" else ""}
                    """.trimIndent()
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "message/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    startActivity(context,createChooser(intent, "Share film"),null)
}