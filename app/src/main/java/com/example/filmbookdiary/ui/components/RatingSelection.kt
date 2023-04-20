package com.example.filmbookdiary.ui.components

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.filmbookdiary.FilmBookDiaryApplication.Companion.maxRating
import com.example.filmbookdiary.R
import com.example.filmbookdiary.viewmodel.SingleFilmViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Float.max
import java.util.*
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RatingSelection(sheetState: ModalBottomSheetState, singleFilmViewModel: SingleFilmViewModel) {

    RatingSelectionRow(sheetState, singleFilmViewModel)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RatingSelectionRow(sheetState: ModalBottomSheetState, singleFilmViewModel: SingleFilmViewModel) {

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

//@OptIn(ExperimentalMaterialApi::class)
//@Preview(showBackground = true)
//@Composable
//fun RatingSelectionRowPreview() {
//    RatingSelectionRow(ModalBottomSheetState)
//}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TextButtonElement(listState: LazyListState, item: Int, sheetState: ModalBottomSheetState, singleFilmViewModel: SingleFilmViewModel) {
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
                singleFilmViewModel.updateFilm { oldFilm ->
                    oldFilm.copy(rating = item)
                }
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

// TODO Внизу либо удалить, либо использовать для остановки на нужном элементе
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SnappyLazyRow(sheetState: ModalBottomSheetState) {

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val list = List(50) { it + 1 }

    LazyRow(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        content = {
            items(list) { index ->
                TextButtonElement(listState = listState, item = index, sheetState = sheetState, singleFilmViewModel = SingleFilmViewModel(UUID.randomUUID())
                )

                if(!listState.isScrollInProgress){
                    if(listState.isHalfPastItemLeft())
                        coroutineScope.scrollBasic(listState, left = true)
                    else
                        coroutineScope.scrollBasic(listState)

                    if(listState.isHalfPastItemRight())
                        coroutineScope.scrollBasic(listState)
                    else
                        coroutineScope.scrollBasic(listState, left = true)
                }
            }
        })
}

private fun CoroutineScope.scrollBasic(listState: LazyListState, left: Boolean = false){
    launch {
        val pos = if(left) listState.firstVisibleItemIndex else listState.firstVisibleItemIndex+1
        listState.animateScrollToItem(pos)
    }
}

@Composable
private fun LazyListState.isHalfPastItemRight(): Boolean {
    return firstVisibleItemScrollOffset > 500
}

@Composable
private fun LazyListState.isHalfPastItemLeft(): Boolean {
    return firstVisibleItemScrollOffset <= 500
}

//@Preview
//@Composable
//fun SnappyLazyRowPreview() {
//    SnappyLazyRow()
//}