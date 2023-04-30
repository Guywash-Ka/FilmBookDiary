package com.example.filmbookdiary.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.filmbookdiary.FilmBookDiaryApplication.Companion.maxRating
import kotlinx.coroutines.launch
import java.lang.Float.max
import java.util.*
import kotlin.math.absoluteValue
import kotlin.reflect.KSuspendFunction1

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RatingSelection(sheetState: ModalBottomSheetState, singleFilmViewModel: KSuspendFunction1<Int, Unit>) {

    RatingSelectionRow(sheetState, singleFilmViewModel)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RatingSelectionRow(sheetState: ModalBottomSheetState, singleFilmViewModel: KSuspendFunction1<Int, Unit>) {

    val list = List(maxRating) { it + 1 }
    val listState = rememberLazyListState()
    LazyRow(state = listState) {
        items(
            list,
            key  = { it }
        ) { item ->
            TextButtonElement(listState = listState, item = item, sheetState = sheetState, singleFilmViewModel)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TextButtonElement(listState: LazyListState, item: Int, sheetState: ModalBottomSheetState, singleFilmViewModel: KSuspendFunction1<Int, Unit>) {
    val coroutineScope = rememberCoroutineScope()

    TextButton(
        modifier = Modifier
            .padding(12.dp)
            .graphicsLayer {
                alpha =
                    max(1 - listState.layoutInfo.normalizedItemPosition(item).absoluteValue, 0.2f)
            },
        content = {
            Text(
                text = item.toString(),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 50.sp
            )
        },
        onClick = {
            coroutineScope.launch {
                singleFilmViewModel(item)
                sheetState.hide()
            }

        }
    )
}

fun LazyListLayoutInfo.normalizedItemPosition(key: Any) : Float =
    visibleItemsInfo
        .firstOrNull { it.key == key }
        ?.let {
            val center = (viewportEndOffset + viewportStartOffset - it.size) / 2F
            (it.offset.toFloat() - center) / center
        }
        ?: 0F