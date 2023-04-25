package com.example.filmbookdiary.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardProgressBar(
    modifier: Modifier,
    width: Dp,
    foregroundColor: Brush,
    percent: Int,
) {
    Box(
        modifier = modifier.width(width)
    ) {
        Box(
            modifier = modifier
                .background(foregroundColor)
                .width(width * percent / 100)
        )
    }
}

@Preview
@Composable
fun CardProgressBarPreview(
) {
    CardProgressBar(
        modifier = Modifier.clip(shape = RoundedCornerShape(4.dp)).height(8.dp),
        width = 300.dp,
        foregroundColor = Brush.horizontalGradient(listOf(Color(0xFF02C39A), Color(0xffF0F3BD))),
        percent = 75,
    )
}