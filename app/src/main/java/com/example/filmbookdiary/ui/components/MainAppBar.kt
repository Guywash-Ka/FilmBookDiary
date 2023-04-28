package com.example.filmbookdiary.ui.components

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.filmbookdiary.R
import com.example.filmbookdiary.data.WidgetState
import com.example.filmbookdiary.data.FilterState
import com.example.filmbookdiary.ui.theme.secondaryTextColor
import com.example.filmbookdiary.ui.theme.textColor

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
                    text = translateTitle(title)
                )
            },
            elevation = 0.dp,
            backgroundColor = colors.background,
            contentColor = colors.background,
            actions = {
                IconButton(
                    onClick = { onSearchClicked() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = secondaryTextColor
                    )
                }
                when (filterWidgetState) {
                    WidgetState.CLOSED -> {
                        IconButton(onClick = { onFilterClicked() }) {
                            Icon(
                                painter = painterResource(R.drawable.outline_filter_alt_24),
                                contentDescription = "Filter Icon",
                                tint = secondaryTextColor
                            )
                        }
                    }
                    WidgetState.OPENED -> {
                        IconButton(onClick = { onCloseClicked() }) {
                            Icon(
                                painter = painterResource(R.drawable.outline_filter_alt_off_24),
                                contentDescription = "Close Icon",
                                tint = secondaryTextColor
                            )
                        }
                    }
                }

            }
        )
        if (filterWidgetState == WidgetState.OPENED){
            Row(Modifier.fillMaxWidth().background(color = colors.background), horizontalArrangement = Arrangement.SpaceEvenly) {
                TextButton(content = { Text("по названию", color = textColor) }, onClick = { onFilterSelectClicked(
                    FilterState.NAME) })
                TextButton(content = { Text("по времени", color = textColor) }, onClick = { onFilterSelectClicked(
                    FilterState.DATE) })
                TextButton(content = { Text("по рейтингу", color = textColor) }, onClick = { onFilterSelectClicked(
                    FilterState.RATING) })
            }
        }
        Canvas(modifier = Modifier.fillMaxWidth(1f)) {

            // Fetching width and height for
            // setting start x and end y
            val canvasWidth = size.width
            val canvasHeight = size.height

            // drawing a line between start(x,y) and end(x,y)
            drawLine(
                start = Offset(x = 60f, y = 0f),
                end = Offset(x = canvasWidth - 60, y = 0f),
                color = Color.White,
                strokeWidth = 4F
            )
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
        color = colors.background
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
                    text = "Название...",
                    color = textColor
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
                        tint = secondaryTextColor
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
                        tint = secondaryTextColor
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
                cursorColor = textColor.copy(alpha = ContentAlpha.medium)
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

fun translateTitle(title: String): String {
    return when (title.lowercase()) {
        "films" -> "Фильмы"
        "books" -> "Книги"
        "profile" -> "Профиль"
        else -> " "
    }
}