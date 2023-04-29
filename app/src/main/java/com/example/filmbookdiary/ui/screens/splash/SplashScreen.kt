package com.example.filmbookdiary.ui.screens.splash

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
                        Color.White
                    )
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn() + expandHorizontally(),
                    exit = fadeOut()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.splash_image),
                        contentDescription = "$it",
                        alignment = Alignment.Center,
                    )
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