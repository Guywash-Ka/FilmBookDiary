package com.example.filmbookdiary.ui.components

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.filmbookdiary.FilmBookDiaryApplication.Companion.maxRating
import com.example.filmbookdiary.R
import com.example.filmbookdiary.ui.theme.secondaryTextColor
import com.example.filmbookdiary.ui.theme.textColor
import com.skydoves.landscapist.glide.GlideImage
import java.util.*

@Composable
fun FilmCard(
    modifier: Modifier = Modifier,
    filmID: UUID,
    filmImageUri: Uri,
    filmName: String,
    filmDescription: String,
    filmRating: Int?,
    filmAuthor: String?,
    filmEmoji: String,
    onFilmClicked: (UUID) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(1f).padding(horizontal = 12.dp, vertical = 5.dp).clickable { onFilmClicked(filmID) }, shape = RoundedCornerShape(10.dp),
    ) {
        Column() {
            GlideImage(
                imageModel = { filmImageUri },
                modifier = modifier
                    .height(220.dp)
                    .fillMaxWidth(1f)
                    .padding(all = 10.dp)
                    .graphicsLayer {
                        clip = true
                        shape = RoundedCornerShape(10.dp)
                    }
            )
            Row(
                modifier = modifier.fillMaxWidth(1f).padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    modifier = Modifier.weight(6f),
                    text = filmName,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight(600),
                    fontSize = 20.sp,
                    color = textColor
                )
                Text(text = filmEmoji, fontSize = 20.sp, modifier = Modifier.weight(1f))
            }
            Text(
                text = filmDescription,
                fontWeight = FontWeight(400),
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = textColor,
                modifier = modifier.padding(start = 10.dp, end = 80.dp, bottom = 10.dp)
            )
            Row( verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(start = 10.dp, end = 80.dp)
            ){
                if (filmAuthor != null) {
                    Icon(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = "Author icon",
                        modifier = modifier
                            .width(24.dp)
                            .height(24.dp),
                        tint = secondaryTextColor
                    )

                    Text(
                        text = filmAuthor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight(600),
                        fontSize = 16.sp,
                        color = secondaryTextColor
                    )
                }
            }
            Box(
                modifier = modifier.fillMaxWidth(1f).padding(10.dp)
            )
            {
                CardProgressBar(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(2.dp))
                        .height(8.dp),
                    width = LocalConfiguration.current.screenWidthDp.dp,
                    foregroundColor = Brush.horizontalGradient(listOf(Color(0xFFF0F3BD), Color(0xFF02C39A))),
                    percent = 100 / maxRating * (filmRating?: 0),
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FilmCardPreview() {
    FilmCard(
        modifier = Modifier,
        filmID = UUID.randomUUID(),
        filmImageUri = Uri.parse("android.resource://com.example.filmbookdiary/" + R.drawable.empty_photo),
        filmName = "Drive",
        filmDescription = "Some film description that will be interesting to everyone",
        filmRating = 7,
        filmAuthor = "J.K. Rowling",
        filmEmoji = "🤠",
        onFilmClicked = {}
    )
}