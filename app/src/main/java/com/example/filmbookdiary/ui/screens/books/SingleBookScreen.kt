package com.example.filmbookdiary.ui.screens.books

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.filmbookdiary.data.WidgetState
import com.example.filmbookdiary.ui.components.RatingSelection
import com.example.filmbookdiary.ui.components.SinglePicture
import com.example.filmbookdiary.ui.screens.films.ShowFilmScreen
import com.example.filmbookdiary.ui.theme.backgroundColor
import com.example.filmbookdiary.ui.theme.secondaryTextColor
import com.example.filmbookdiary.ui.theme.textColor
import com.example.filmbookdiary.viewmodel.SingBookViewModelFactory
import com.example.filmbookdiary.viewmodel.SingleBookViewModel
import kotlinx.coroutines.launch
import java.util.*
import kotlin.reflect.KFunction1

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SingleBookScreen(
    modifier: Modifier = Modifier,
    bookID: String? = UUID.randomUUID().toString(),
    navigateBack: () -> Unit,
    singleBookViewModel: SingleBookViewModel = viewModel(factory = SingBookViewModelFactory(UUID.fromString(bookID))),
) {
    val isEdited = remember { mutableStateOf(false) }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val scope = rememberCoroutineScope()

    val book = singleBookViewModel.book.collectAsState(null).value

    val bookName = book?.name
    val bookAuthor = book?.author
    val bookImageUri = book?.imageUri
    val bookDescription = book?.description
    val bookRating = book?.rating
    val bookIsRead = book?.isRead
    val bookEmoji = book?.emoji

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        imageUri?.let {
            singleBookViewModel.updateBook { oldBook ->
                oldBook.copy(imageUri = imageUri!!)
            }
        }
    }

    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden
    )

    fun updateBookRating(rating: Int) {
        singleBookViewModel.updateBook { oldBook ->
            oldBook.copy(rating = rating)
        }
    }

    fun updateBookIsRead(isRead: Boolean) {
        singleBookViewModel.updateBook { oldBook ->
            oldBook.copy(isRead = isRead)
        }
    }

    fun updateBookEmoji(emoji: String) {
        singleBookViewModel.updateBook { oldBook ->
            oldBook.copy(emoji = emoji)
        }
    }

    fun updateBookName(name: String) {
        singleBookViewModel.updateBook { oldBook ->
            oldBook.copy(name = name)
        }
    }

    fun updateBookDescription(description: String) {
        singleBookViewModel.updateBook { oldBook ->
            oldBook.copy(description = description)
        }
    }

    fun updateBookAuthor(author: String) {
        singleBookViewModel.updateBook { oldBook ->
            oldBook.copy(author = author)
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            RatingSelection(sheetState, ::updateBookRating)
        },
        sheetContentColor = colors.background,
        sheetBackgroundColor = colors.surface,
    ) {
        Column(modifier = modifier
            .verticalScroll(enabled = true, state = ScrollState(0))
//            .background(color = Color.White)
            .fillMaxHeight(1f)) {
            Box(contentAlignment = Alignment.Center) {
                SinglePicture(
                    singleImageUri = bookImageUri,
                    imageUri = imageUri,
                    modifier = modifier
                )
                if (isEdited.value) {
                    FloatingActionButton(
                        onClick = { launcher.launch("image/*") },
                        modifier = modifier
                            .align(Alignment.BottomEnd)
                            .padding(6.dp)
                            .size(26.dp))
                    {
                        Icon(Icons.Filled.Edit, contentDescription = "Change image", Modifier.size(20.dp))
                    }
                    FloatingActionButton(
                        onClick = {
                            scope.launch {
                                if (book != null) {
                                    singleBookViewModel.removeBook(book)
                                    navigateBack()
                                }
                            }
                        },
                        modifier = modifier
                            .align(Alignment.TopEnd)
                            .padding(6.dp)
                            .size(26.dp))
                    {
                        Icon(Icons.Filled.Clear, contentDescription = "Delete Book", Modifier.size(20.dp))
                    }
                }
            }
            if (isEdited.value) {
                EditBookScreen(
                    bookName = bookName,
                    bookAuthor = bookAuthor,
                    bookDescription = bookDescription,
                    bookRating = bookRating,
                    bookEmoji = bookEmoji,
                    isEdited = isEdited,
                    updateName = ::updateBookName,
                    updateDescription = ::updateBookDescription,
                    updateAuthor = ::updateBookAuthor,
                )
            } else {
                ShowBookScreen(
                    modifier = modifier,
                    bookName = bookName,
                    bookAuthor = bookAuthor,
                    bookDescription = bookDescription,
                    bookRating = bookRating,
                    bookIsRead = bookIsRead,
                    bookEmoji = bookEmoji,
                    sheetState = sheetState,
                    isEdited = isEdited,
                    updateIsRead = ::updateBookIsRead,
                    updateEmoji = ::updateBookEmoji,
                )
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
    bookEmoji: String?,
    isEdited: MutableState<Boolean>,
    updateName: KFunction1<String, Unit>,
    updateDescription: KFunction1<String, Unit>,
    updateAuthor: KFunction1<String, Unit>,
) {
    var name by remember { mutableStateOf(bookName) }
    var author by remember { mutableStateOf(bookAuthor) }
    var description by remember { mutableStateOf(bookDescription) }
    var rating by remember { mutableStateOf(bookRating) }
    Column(
        modifier = Modifier.fillMaxWidth(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            label = { Text("Автор") }
        )
        
        description?.let {
            OutlinedTextField(
                value = it,
                leadingIcon = { Icon(imageVector = Icons.Default.Info, contentDescription = "info") },
                onValueChange = { newDescriptionText ->
                    description = newDescriptionText
                    updateDescription(description ?: " ")
                },
                label = { Text("Описание") }
            ) }
    }
    
    IconButton(onClick = { 
        isEdited.value = !isEdited.value
    }) {
        Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Back")
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowBookScreen(
    modifier: Modifier,
    bookName: String?,
    bookAuthor: String?,
    bookRating: Int?,
    bookDescription: String?,
    bookIsRead: Boolean?,
    bookEmoji: String?,
    sheetState: ModalBottomSheetState,
    isEdited: MutableState<Boolean>,
    updateIsRead: KFunction1<Boolean, Unit>,
    updateEmoji: KFunction1<String, Unit>,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val emojiRowState = remember { mutableStateOf(WidgetState.CLOSED) }
    val emojiList = listOf("😎", "😂", "😡", "🤠", "😭", "❤", "😴", "😱", "💔", "🔫", "🧟", "🤡")

    Card(shape = RoundedCornerShape(10.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            bookName?.let {
                Text(
                    modifier = modifier.padding(start = 8.dp, bottom = 16.dp),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp,
                    text = bookName,
                    color = textColor
                ) }
            bookAuthor?.let {
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
                        text = bookAuthor,
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
                        if (bookIsRead == null || !bookIsRead) {
                            updateIsRead(true)
                        } else {
                            updateIsRead(false)
                        }
                    }
                    Toast.makeText(
                        context,
                        if (bookIsRead == null || !bookIsRead) {
                            "Фильм просмотрен!"
                        } else {
                            "Надо посмотреть!"
                        },
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                    if (bookIsRead == true){
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
                        bookRating?.let {
                            Text(
                                modifier = modifier.fillMaxHeight(1f),
//                        textAlign = TextAlign.Justify,
                                fontWeight = FontWeight.ExtraBold,
                                text = "$bookRating",
                                fontSize = 20.sp,
                                color = when(bookRating){
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
                                tint = if (bookRating != null) {
                                    when(bookRating) {
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
                    Text(text = bookEmoji ?: "\uD83D\uDE34", fontSize = 20.sp, modifier = modifier)
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
                    shareBook(context, bookName ?: " ", bookRating)
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

            bookDescription?.let {
                Text(
                    modifier = modifier
                        .padding(start = 16.dp, bottom = 8.dp)
                        .fillMaxHeight(1f),
                    text = bookDescription,
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
fun ShowBookScreenPreview() {
    fun someFun(boolArg: Boolean) {
    }
    fun someFun2(stringArg: String) {
    }

    ShowBookScreen(
        modifier = Modifier,
        bookName = "Book name",
        bookRating = 6,
        bookDescription = "Very cool book",
        bookAuthor = "Book Author",
        bookIsRead = false,
        bookEmoji = "\uD83D\uDE0E",
        sheetState = ModalBottomSheetState(ModalBottomSheetValue.Hidden),
        isEdited = mutableStateOf(false),
        updateIsRead = ::someFun,
        updateEmoji = ::someFun2,
    )
}

fun shareBook(context: Context, bookName: String, bookRating: Int?) {
    val message = """
                        Очень рекомендую прочитать книгу "$bookName"!
                        
                        ${if (bookRating != null) "Я оценил её на $bookRating баллов" else ""}
                    """.trimIndent()
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "message/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    ContextCompat.startActivity(context, Intent.createChooser(intent, "Share book"), null)
}