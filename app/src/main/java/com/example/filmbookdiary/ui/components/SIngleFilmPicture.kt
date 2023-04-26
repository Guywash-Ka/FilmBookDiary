package com.example.filmbookdiary.ui.components

import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun SingleFilmPicture(
    filmImageUri: Uri?,
    imageUri: Uri?,
    modifier: Modifier

    ) {
    GlideImage(
        imageModel = { imageUri ?: filmImageUri },
        modifier = modifier
            .height(370.dp)
            .fillMaxWidth(1f)
            .blur(15.dp),
        imageOptions = ImageOptions(
            contentScale = ContentScale.Crop
        )
    )
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
        Canvas(
            modifier = modifier
                .height(370.dp)
                .fillMaxWidth(1f),
        ) {
            drawRect(
                alpha = 0.7F,
                color = androidx.compose.ui.graphics.Color.Black,
                size = size
            )
        }
    }
    GlideImage(
        imageModel = { imageUri ?: filmImageUri },
        modifier = modifier
            .height(250.dp)
            .width(250.dp),
        imageOptions = ImageOptions(
            contentScale = ContentScale.Inside
        )
    )
}