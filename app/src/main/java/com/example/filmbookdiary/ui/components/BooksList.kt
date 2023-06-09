package com.example.filmbookdiary.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.filmbookdiary.FilmBookDiaryApplication.Companion.maxRating
import com.example.filmbookdiary.R
import com.example.filmbookdiary.data.Book
import com.example.filmbookdiary.viewmodel.BookViewModel
import java.util.UUID

@Composable
fun BookPage(
    modifier: Modifier = Modifier,
    bookID: UUID,
    bookImageUri: Uri,
    bookName: String,
    bookAuthor: String,
    bookDescription: String,
    bookRating: Int?,
    bookEmoji: String,
    onBookClicked: (UUID) -> Unit,
) {
    BookCard(
        bookID = bookID,
        bookImageUri = bookImageUri,
        bookName = bookName,
        bookDescription = bookDescription,
        bookRating = bookRating,
        bookAuthor = bookAuthor,
        bookEmoji = bookEmoji,
        onBookClicked = onBookClicked
    )
}

@Composable
fun BookList(
    modifier: Modifier,
    books: List<Book>,
    onBookClicked: (UUID) -> Unit
) {

    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp), contentPadding = PaddingValues(all = 8.dp)) {
        items(
            items = books,
            itemContent = {
                BookPage(
                    bookID = it.id,
                    bookImageUri = it.imageUri,
                    bookName = it.name,
                    bookAuthor = it.author,
                    bookDescription = it.description,
                    bookRating = it.rating,
                    bookEmoji = it.emoji,
                    onBookClicked = onBookClicked
                )
            },
        )
    }
}

@Preview
@Composable
fun BookPagePreview(
    modifier: Modifier = Modifier,
    bookID: UUID = UUID.randomUUID(),
    bookImageUri: Uri = Uri.parse("android.resource://com.example.filmbookdiary/" + R.drawable.empty_photo),
    bookName: String = "Sherlock Holms",
    bookAuthor: String = "William Shakespeare",
    bookDescription: String = "Once upon a time lived a family, Once upon a time lived a family, Once upon a time lived a family, Once upon a time lived a family",
    bookRating: Int? = 2
) {

    Card(
        shape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp),
        elevation = 10.dp,
        modifier = modifier.clickable {  }
    ) {
        Box() {

            Row(modifier = modifier.background(color = colors.primary)) {
                Image(
                    painter = painterResource(R.drawable.empty_photo),
                    contentDescription = "Book image",
                    contentScale = ContentScale.FillHeight,
                    modifier = modifier
                        .width(140.dp)
                        .height(240.dp)
                )
                Row(
                    modifier = modifier.fillMaxWidth(1f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column() {
                        Text(
                            modifier = modifier.padding(start = 8.dp, end = 30.dp),
                            fontWeight = FontWeight.ExtraBold,
                            text = bookName,
                            style = typography.h4,
                            color = colors.onPrimary,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            modifier = modifier.padding(start = 8.dp),
                            fontWeight = FontWeight.Bold,
                            text = bookAuthor,
                            style = typography.h6,
                            color = colors.onPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            modifier = modifier.padding(start = 16.dp, bottom = 8.dp),
                            text = bookDescription,
                            color = colors.onPrimary,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    if (bookRating != null) {
                        Text(
                            text = "$bookRating/$maxRating",
                            fontWeight = FontWeight.ExtraBold,
                            style = typography.h4,
                            color = colors.onPrimary
                        )
                    }
                }
            }
            IconButton(onClick = {}, modifier.align(Alignment.TopEnd)) {
                Icon(
                    Icons.Filled.Clear,
                    contentDescription = "Remove element",
                    tint = Color.Black
                )
            }
        }
    }
}