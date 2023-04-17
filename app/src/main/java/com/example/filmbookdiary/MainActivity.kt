package com.example.filmbookdiary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.filmbookdiary.nav.DiaryNavHost
import com.example.filmbookdiary.nav.Films
import com.example.filmbookdiary.nav.diaryTabRowScreens
import com.example.filmbookdiary.nav.navigateSingleTopTo
import com.example.filmbookdiary.ui.components.DiaryTabRow
import com.example.filmbookdiary.ui.theme.FilmBookDiaryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiaryApp()
        }
    }
}

@Composable
fun DiaryApp() {
    FilmBookDiaryTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen = diaryTabRowScreens.find { it.route == currentDestination?.route }?: Films
        Scaffold(
            bottomBar = {
                DiaryTabRow(
                    allScreens = diaryTabRowScreens,
                    onTabSelected = { newScreen ->
                        navController.navigateSingleTopTo(newScreen.route)
                    },
                    currentScreen = currentScreen
                )
            }
        ) {innerPadding ->
            DiaryNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )

        }

    }
}