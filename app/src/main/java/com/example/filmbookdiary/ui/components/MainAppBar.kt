package com.example.filmbookdiary.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.filmbookdiary.data.WidgetState
import com.example.filmbookdiary.database.FilterState
import com.example.filmbookdiary.ui.theme.backgroundColor

@Composable
fun MainAppBar(
    title: String,
    searchWidgetState: WidgetState,
    searchTextState: String,
    filterWidgetState: WidgetState,
    filterSelectState: FilterState,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit,
    onFilterSelectClicked: (FilterState) -> Unit,
    onFilterTriggered: () -> Unit
) {
    when (searchWidgetState) {
        WidgetState.CLOSED -> {
            DefaultAppBar(
                title = title,
                onSearchClicked = onSearchTriggered,
                filterWidgetState = filterWidgetState,
                filterSelectState = filterSelectState,
                onFilterClicked = onFilterTriggered,
                onCloseClicked = onCloseClicked,
                onFilterSelectClicked = onFilterSelectClicked
            )
        }
        WidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}

@Composable
fun DefaultAppBar(
    title: String,
    onSearchClicked: () -> Unit,
    filterWidgetState: WidgetState,
    filterSelectState: FilterState,
    onFilterClicked: () -> Unit,
    onCloseClicked: () -> Unit,
    onFilterSelectClicked: (FilterState) -> Unit
) {
    Column() {
        TopAppBar(
            title = {
                Text(
                    text = title
                )
            },
            actions = {
                IconButton(
                    onClick = { onSearchClicked() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
                when (filterWidgetState) {
                    WidgetState.CLOSED -> {
                        IconButton(onClick = { onFilterClicked() }) {
                            Icon(
                                imageVector = Icons.Filled.List,
                                contentDescription = "Filter Icon",
                                tint = Color.White
                            )
                        }
                    }
                    WidgetState.OPENED -> {
                        IconButton(onClick = { onCloseClicked() }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Close Icon",
                                tint = Color.White
                            )
                        }
                    }
                }

            }
        )
        if (filterWidgetState == WidgetState.OPENED)
        Row(Modifier.fillMaxWidth().background(color = colors.primary), horizontalArrangement = Arrangement.SpaceEvenly) {
            TextButton(content = { Text("Name", color = Color.White) }, onClick = { onFilterSelectClicked(FilterState.NAME) })
            TextButton(content = { Text("Date", color = Color.White) }, onClick = { onFilterSelectClicked(FilterState.DATE) })
            TextButton(content = { Text("Rating", color = Color.White) }, onClick = { onFilterSelectClicked(FilterState.RATING) })
        }
    }
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = colors.primary
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            ))
    }
}




@Composable
@Preview
fun DefaultAppBarPreview() {
    DefaultAppBar(
        title = "Films",
        onSearchClicked = {},
        filterWidgetState = WidgetState.OPENED,
        filterSelectState = FilterState.NAME,
        onFilterClicked = {},
        onCloseClicked = {},
        onFilterSelectClicked = {}
    )
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar(
        text = "Some random text",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {}
    )
}