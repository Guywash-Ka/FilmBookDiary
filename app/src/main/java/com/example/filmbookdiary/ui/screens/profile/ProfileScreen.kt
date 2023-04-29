package com.example.filmbookdiary.ui.screens.profile

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.filmbookdiary.data.ProfileElementData
import com.example.filmbookdiary.ui.components.AnimatedCircle
import com.example.filmbookdiary.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = viewModel(),
) {
    val filmsWatchedNumber = profileViewModel.getNumberOfWatchedFilms().collectAsState(0).value
    val filmsNotWatchedNumber = profileViewModel.getNumberOfNotWatchedFilms().collectAsState(0).value

    val booksReadNumber = profileViewModel.getNumberOfReadBooks().collectAsState(0).value
    val booksNotReadNumber = profileViewModel.getNumberOfNotReadBooks().collectAsState(0).value

    val fullNumber = filmsWatchedNumber + filmsNotWatchedNumber + booksReadNumber + booksNotReadNumber

    val listOfElements = listOf(
        ProfileElementData("Просмотренные фильмы", filmsWatchedNumber, Color(0xFF8778D7)),
        ProfileElementData("Непросмотренные фильмы", filmsNotWatchedNumber, Color(0xFFFF80CE)),
        ProfileElementData("Прочитанные книги", booksReadNumber, Color(0xFF00C7E6)),
        ProfileElementData("Непрочитанные книги", booksNotReadNumber, Color(0xFFFEC404))
    )
    val accountsProportion = listOfElements.map { if (it.value > 0) it.value / fullNumber.toFloat() else 0f}
    val circleColors = listOfElements.map { it.color }

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Box(modifier = modifier.padding(16.dp)) {


            AnimatedCircle(
                accountsProportion,
                circleColors,
                Modifier
                    .height(300.dp)
                    .align(Alignment.Center)
                    .fillMaxWidth()
            )

        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            listOfElements.forEach {
                ElementStatusCard(elem = it)
            }
        }

    }
}

@Composable
fun ElementStatusCard(
    elem: ProfileElementData
) {
    val name = elem.name
    val value = elem.value
    val color = elem.color

    Card(modifier = Modifier) {
        Row(modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(modifier = Modifier, horizontalArrangement = Arrangement.Center) {
                Canvas(modifier = Modifier.height(24.dp)) {
                    val canvasHeight = size.height
                    drawLine(
                        start = Offset(x = 0f, y = canvasHeight),
                        end = Offset(x = 0f, y = 0f),
                        strokeWidth = 8f,
                        color = color
                    )
                }
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = name, modifier = Modifier, fontWeight = FontWeight(500), fontSize = 16.sp)
            }
            Text(
                text = value.toString(),
                modifier = Modifier,
                textAlign = TextAlign.End,
                fontWeight = FontWeight(500),
                fontSize = 16.sp
            )
        }
    }
}

@Preview
@Composable
fun ElementStatusCardPreview() {
    ElementStatusCard(ProfileElementData(name = "Фильмы", value = 3, color = Color.Magenta))
}