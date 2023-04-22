package com.example.filmbookdiary.ui.screens.books

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.filmbookdiary.R
import com.example.filmbookdiary.data.Book
import com.example.filmbookdiary.data.FilterState
import com.example.filmbookdiary.ui.components.BookList
import com.example.filmbookdiary.viewmodel.BookViewModel
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun BooksScreen(
    modifier: Modifier = Modifier,
    bookViewModel: BookViewModel = viewModel(),
    navigateToSingleElement: (UUID) -> Unit = {},
    searchTextState: String,
    filterSelectState: FilterState
) {
//    val booksList = bookViewModel.books.collectAsState(emptyList()).value
    val preBooksList = if (searchTextState == "") {
        bookViewModel.books.collectAsState(emptyList()).value.reversed()
    } else {
        bookViewModel.searchBooksByName(searchTextState).collectAsState(emptyList()).value.reversed()
    }
    val booksList = when (filterSelectState) { // TODO СДЕЛАТЬ ЧЕРЕЗ БД
        FilterState.DATE -> preBooksList
        FilterState.NAME -> preBooksList.sortedBy { it.name }
        FilterState.RATING -> preBooksList.sortedBy { it.rating }.reversed()
    }

    val coroutineScope = rememberCoroutineScope()
    Box(modifier = modifier.fillMaxSize(1f)) {
        BookList(
            modifier = modifier,
            books = booksList,
            onBookClicked = navigateToSingleElement,
            bookViewModel = bookViewModel
        )
        FloatingActionButton(
            onClick = {
                var newUUID: UUID
                coroutineScope.launch {
                    newUUID = UUID.randomUUID()
                    bookViewModel.addBook(Book(newUUID, Uri.parse("android.resource://com.example.filmbookdiary/" + R.drawable.empty_photo), "New Book", "William Shakespeare", "Very cool book", null, true))
                    navigateToSingleElement(newUUID)
                }
            },
            modifier = modifier
                .padding(4.dp)
                .align(alignment = Alignment.BottomEnd),
            elevation = FloatingActionButtonDefaults.elevation(4.dp),
            backgroundColor = colors.primaryVariant
        ) {
            Icon(Icons.Filled.Add, "Add new element")
        }

//        FloatingActionButton(
//            onClick = { coroutineScope.launch {
//                bookViewModel.removeAllBooks()
//            }
//            },
//            modifier = modifier
//                .padding(4.dp)
//                .align(alignment = Alignment.BottomStart),
//            elevation = FloatingActionButtonDefaults.elevation(4.dp),
//            backgroundColor = colors.primaryVariant
//        ) {
//            Icon(Icons.Filled.Clear, "Remove all elements")
//        }
    }
}