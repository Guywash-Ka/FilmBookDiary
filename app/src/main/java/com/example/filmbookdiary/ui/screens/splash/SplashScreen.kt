package com.example.filmbookdiary.ui.screens.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColor
import com.example.filmbookdiary.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navigateToMainScreen: () -> Unit) {
    var visible by remember { mutableStateOf(false) }

    Scaffold(
        content = {
            Box(
                modifier = Modifier
                    .background(
                        colors.primary
                    )
                    .fillMaxSize(),
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = slideInVertically(
                        initialOffsetY = {
                            // Slide in from top
                            -it
                        },
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = LinearEasing
                        )
                    ),
                ) {
                    Column(
                        verticalArrangement =
                        Arrangement.Center,
                        horizontalAlignment =
                        Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(it)
                            .background(
                                colors.primary
                            )
                            .fillMaxSize()
                    ) {
                        Text(
                            "FBDiary",
                            fontSize = 36.sp,
                            modifier = Modifier.padding(
                                bottom =
                                100.dp
                            ),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Box(
                            modifier = Modifier
                                .height(150.dp)
                                .width(150.dp)
                                .clip(
                                    RoundedCornerShape(
                                        8.dp
                                    )
                                )
                        ) {
                            Image(painter = painterResource(id = R.drawable.start_photo2), contentDescription = "start photo")
                        }
                    }
                }
            }
            LaunchedEffect(true) {
                visible = true
                delay(2000)
                navigateToMainScreen()
            }
        }
    )
}