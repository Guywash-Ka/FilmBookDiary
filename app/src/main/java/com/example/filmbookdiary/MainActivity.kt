package com.example.filmbookdiary

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.filmbookdiary.data.SearchWidgetState
import com.example.filmbookdiary.nav.*
import com.example.filmbookdiary.ui.components.DiaryTabRow
import com.example.filmbookdiary.ui.components.MainAppBar
import com.example.filmbookdiary.ui.theme.FilmBookDiaryTheme
import com.example.filmbookdiary.viewmodel.TopBarViewModel
import java.util.*

class MainActivity : ComponentActivity() {

    private val topBarViewModel: TopBarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiaryApp(topBarViewModel = topBarViewModel)
        }
    }
}

@Composable
fun DiaryApp(topBarViewModel: TopBarViewModel) {
    FilmBookDiaryTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen = diaryTabRowScreens.find { it.route == currentDestination?.route }?: Splash

        val searchWidgetState by topBarViewModel.searchWidgetState
        val searchTextState by topBarViewModel.searchTextState
        Scaffold(
            topBar = {
                if (currentScreen != Splash) {
                    MainAppBar(
                        title = currentScreen.route.replaceFirstChar {// Capitalize
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ROOT
                            ) else it.toString()
                        },
                        searchWidgetState = searchWidgetState,
                        searchTextState = searchTextState,
                        onTextChange = {
                            topBarViewModel.updateSearchTextState(newValue = it)
                        },
                        onCloseClicked = {
                            topBarViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                        },
                        onSearchClicked = {
                            Log.d("Searched Text", it)
                        },
                        onSearchTriggered = {
                            topBarViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                        }
                    )
                }
            },
            bottomBar = {
                if (currentScreen != Splash) {
                    DiaryTabRow(
                        allScreens = diaryTabRowScreens,
                        onTabSelected = { newScreen ->
                            navController.navigateSingleTopTo(newScreen.route)
                        },
                        currentScreen = currentScreen
                    )
                }
            }
        ) {innerPadding ->
            DiaryNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                searchTextState = searchTextState
            )

        }

    }
}