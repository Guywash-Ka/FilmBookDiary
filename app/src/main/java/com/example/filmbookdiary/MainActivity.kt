package com.example.filmbookdiary

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.filmbookdiary.data.WidgetState
import com.example.filmbookdiary.database.FilterState
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
        val filterWidgetState by topBarViewModel.filterWidgetState
        val filterSelectState by topBarViewModel.filterSelectState
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
                        filterWidgetState = filterWidgetState,
                        filterSelectState = filterSelectState,
                        onTextChange = {
                            topBarViewModel.updateSearchTextState(newValue = it)
                        },
                        onCloseClicked = {
                            topBarViewModel.updateSearchWidgetState(newValue = WidgetState.CLOSED)
                            topBarViewModel.updateFilterWidgetState(newValue = WidgetState.CLOSED)
                        },
                        onSearchClicked = {
                            Log.d("Searched Text", it)
                        },
                        onSearchTriggered = {
                            topBarViewModel.updateSearchWidgetState(newValue = WidgetState.OPENED)
                        },
                        onFilterSelectClicked = {
                            topBarViewModel.updateFilterSelectState(newValue = it)
                        },
                        onFilterTriggered = {
                            topBarViewModel.updateFilterWidgetState(newValue = WidgetState.OPENED)
                        }
,                    )
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
                searchTextState = searchTextState,
                filterSelectState = filterSelectState
            )

        }

    }
}