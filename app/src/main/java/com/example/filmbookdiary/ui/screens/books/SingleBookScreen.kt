package com.example.filmbookdiary.ui.screens.books

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.filmbookdiary.FilmBookDiaryApplication.Companion.maxRating
import com.example.filmbookdiary.ui.components.RatingSelection
import com.example.filmbookdiary.viewmodel.SingBookViewModelFactory
import com.example.filmbookdiary.viewmodel.SingleBookViewModel
import com.example.filmbookdiary.viewmodel.SingleFilmViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SingleBookScreen(
    modifier: Modifier = Modifier,
    bookID: String? = UUID.randomUUID().toString(),
    singleBookViewModel: SingleBookViewModel = viewModel(factory = SingBookViewModelFactory(UUID.fromString(bookID))),
) {
    val isEdited = remember { mutableStateOf(false) }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val book = singleBookViewModel.book.collectAsState(null).value

    val bookName = book?.name
    val bookAuthor = book?.author
    val bookImageUri = book?.imageUri
    val bookDescription = book?.description
    val bookRating = book?.rating
    val bookIsRead = book?.isRead

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        singleBookViewModel.updateBook { oldBook ->
            oldBook.copy(imageUri = imageUri!!)
        }
    }

    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()

    fun updateBook(rating: Int) {
        singleBookViewModel.updateBook { oldBook ->
            oldBook.copy(rating = rating)
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            RatingSelection(sheetState, ::updateBook)
        },
        sheetBackgroundColor = colors.surface,
    ) {
        Column(modifier = modifier.verticalScroll(enabled = true, state = ScrollState(0))) {
            Box(contentAlignment = Alignment.BottomEnd) {
                GlideImage(
                    imageModel = { imageUri ?: bookImageUri },
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
                EditBookScreen(
                    bookName = bookName,
                    bookAuthor = bookAuthor,
                    bookDescription = bookDescription,
                    bookRating = bookRating,
                    singleBookViewModel = singleBookViewModel
                )
            } else {
                ShowBookScreen(
                    modifier = modifier,
                    bookName = bookName,
                    bookAuthor = bookAuthor,
                    bookDescription = bookDescription,
                    bookRating = bookRating,
                    bookIsRead = bookIsRead
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
fun EditBookScreen(
    bookName: String?,
    bookAuthor: String?,
    bookDescription: String?,
    bookRating: Int?,
    singleBookViewModel: SingleBookViewModel
) {
    var name by remember { mutableStateOf(bookName) }
    var author by remember { mutableStateOf(bookAuthor) }
    var description by remember { mutableStateOf(bookDescription) }
    var rating by remember { mutableStateOf(bookRating) }

    name?.let {
        TextField(
            value = it,
            onValueChange = { newNameText ->
                if (!newNameText.contains("\n")) {
                    name = newNameText
                    singleBookViewModel.updateBook { oldBook ->
                        oldBook.copy(name = name!!)
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
    author?.let {
        TextField(
            value = it,
            onValueChange = { newAuthorText ->
                if (!newAuthorText.contains("\n")) {
                    author = newAuthorText
                    singleBookViewModel.updateBook { oldBook ->
                        oldBook.copy(author = author!!)
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
                singleBookViewModel.updateBook { oldBook ->
                    oldBook.copy(description = description!!)
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
fun ShowBookScreen(
    modifier: Modifier,
    bookName: String?,
    bookAuthor: String?,
    bookRating: Int?,
    bookDescription: String?,
    bookIsRead: Boolean?
) {
    bookName?.let {
        Text(
            modifier = modifier.padding(start = 8.dp),
            fontWeight = FontWeight.ExtraBold,
            text = bookName,
            style = MaterialTheme.typography.h4,
        ) }

    bookAuthor?.let {
        Text(
            modifier = modifier.padding(start = 8.dp),
            fontWeight = FontWeight.Bold,
            text = bookAuthor,
            style = MaterialTheme.typography.h6,
        ) }

    bookRating?.let {
        Text(
            modifier = modifier.padding(start = 8.dp),
            fontWeight = FontWeight.ExtraBold,
            text = "$bookRating/$maxRating",
            style = MaterialTheme.typography.h4,
        )
    }

    bookDescription?.let {
        Text(
            modifier = modifier.padding(start = 16.dp, bottom = 8.dp),
            text = bookDescription,
        )
    }

    bookIsRead?.let {
        Text(text = if (bookIsRead) {
            "Прочитано!"
        } else {
            "Непрочитано!"
        },
            style = typography.h4
        )
    }
}